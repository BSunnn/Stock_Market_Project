package com.example.myapplication.ui.companylist

import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.myapplication.database.StockItemsDatabase
import com.example.myapplication.network.FinHubApi
import com.example.myapplication.network.StockCompanyApi
import com.example.myapplication.model.StockCompany
import javax.inject.Inject

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
                remoteKey?.prevKey?: return MediatorResult.Success(true)
            }

            LoadType.APPEND -> {
                val lastOne = state.lastItemOrNull() ?: return MediatorResult.Success(false)
                val remoteKeys = database.remoteKeysDao().remoteKeysBySymbol(lastOne.symbol)
                remoteKeys?.nextKey ?: return MediatorResult.Success(true)
            }



            else -> {}
        }

        return MediatorResult.Success(true)

    }

}
