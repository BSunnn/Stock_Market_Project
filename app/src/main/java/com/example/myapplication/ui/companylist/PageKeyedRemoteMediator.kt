package com.example.myapplication.ui.companylist

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.myapplication.database.StockItemsDatabase
import com.example.myapplication.model.RemoteKeys
import com.example.myapplication.network.FinHubApi
import com.example.myapplication.network.StockCompanyApi
import com.example.myapplication.model.StockCompany
import kotlinx.coroutines.channels.ticker
import retrofit2.HttpException
import javax.inject.Inject
import kotlin.math.ceil

class PageKeyedRemoteMediator @Inject constructor(
    private val database: StockItemsDatabase,
    private val stockCompanyApi: StockCompanyApi,
    private val finHubApi: FinHubApi
) : RemoteMediator<Int, StockCompany>() {

    private val stockIndex = "^NDX"

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, StockCompany>
    ): MediatorResult {

        val page = when(loadType) {
            LoadType.PREPEND -> {
                val firstOne =  state.firstItemOrNull() ?: return MediatorResult.Success(true)
                val remoteKey = database.remoteKeysDao().remoteKeysBySymbol(firstOne.symbol)
                remoteKey?.prevKey ?: return MediatorResult.Success(true)
            }

            LoadType.APPEND -> {
                val lastOne = state.lastItemOrNull() ?: return MediatorResult.Success(false)
                val remoteKeys = database.remoteKeysDao().remoteKeysBySymbol(lastOne.symbol)
                remoteKeys?.nextKey ?: return MediatorResult.Success(true)
            }

            LoadType.REFRESH -> {
                val remoteKey = getRemoteKeyIntoCurrent(state)
                remoteKey?.currentKey ?: 0
            }
        }

        try {
            val endOfPaginationReached = database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.remoteKeysDao().clearKeys()
                    database.stockItemDao().clearStockItems()

                    val key = loadRemoteKey(state.config.pageSize)
                    database.remoteKeysDao().insertAll(key)
                }
                val stockItems = loadPage(page)
                database.stockItemDao().insertAll(stockItems)

                val savedSymbols = database.savedDao()
                    .filterSaved(stockItems.map { it.symbol }).map { it.symbol }
                database.stockItemDao().save(savedSymbols)

                return@withTransaction stockItems.isEmpty()
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun loadRemoteKey(pageSize: Int): List<RemoteKeys> {
        val symbols = loadSymbol(stockIndex)

        val pageNumber = ceil(symbols.size.toDouble() / pageSize)

        return symbols.mapIndexed { index, symbol ->
            val currentKey = index / pageSize
            val nextKey = if (currentKey + 1 < pageNumber) currentKey + 1 else null

            val preKey = if (currentKey > 0) currentKey - 1 else null
            RemoteKeys(symbol, currentKey, preKey, nextKey)
        }
    }

    private suspend fun loadSymbol(stockIndex: String): List<String> {
       return finHubApi.getConstituents(stockIndex).symbol.sorted()
    }

    private suspend fun loadPage(page: Int): List<StockCompany> {
        val symbol = database.remoteKeysDao().remoteKeysByPage(page).map { it.symbol }

        val query = symbol.joinToString(",")
        return stockCompanyApi.getStockItems(
            query
        )
    }

    private suspend fun getRemoteKeyIntoCurrent(
        state: PagingState<Int, StockCompany>
    ): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.symbol?.let { symbolId ->
                database.remoteKeysDao().remoteKeysBySymbol(symbolId)
            }
        }
    }
}
