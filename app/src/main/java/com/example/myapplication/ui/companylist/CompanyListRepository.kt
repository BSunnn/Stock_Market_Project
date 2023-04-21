package com.example.myapplication.ui.companylist


import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.room.withTransaction
import com.example.myapplication.database.StockItemsDatabase
import com.example.myapplication.model.SavedStockItem
import com.example.myapplication.model.StockCompany
import com.example.myapplication.network.FinHubApi
import com.example.myapplication.network.StockCompanyApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CompanyListRepository @Inject constructor(
    val finHubApi: FinHubApi,
    val stockCompanyApi: StockCompanyApi,
    val database: StockItemsDatabase
) {
    fun allItems(stockIndex: String): Flow<PagingData<StockCompany>> {

        return Pager(
            config = PagingConfig(
                enablePlaceholders = true,
                pageSize = PAGE_SIZE,

            ),
            remoteMediator = PageKeyedRemoteMediator(
                database,
                stockCompanyApi,
                finHubApi,
            ),
            pagingSourceFactory = { database.stockItemDao().getAll() }
        ).flow
    }

    fun savedItems(): Flow<PagingData<StockCompany>> {

        return Pager(
            config = PagingConfig(
                enablePlaceholders = true,
                pageSize = PAGE_SIZE,

                ),
            pagingSourceFactory = { database.stockItemDao().getSaved() }
        ).flow
    }

    suspend fun saved(symbol: String) {
        database.withTransaction {
            database.savedDao().save(SavedStockItem(symbol))
            database.stockItemDao().save(listOf(symbol))
        }
    }

    suspend fun unSave(symbol: String) {
        database.withTransaction {
            database.savedDao().unSaved(SavedStockItem(symbol))
            database.stockItemDao().unSave(listOf(symbol))
        }
    }

    suspend fun isSaved(symbol: String) : Boolean{
        return database.savedDao().isSaved(symbol) == 1
    }

    companion object {
        const val PAGE_SIZE = 15
    }

}
