package com.example.myapplication.ui.companylist


import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.myapplication.database.StockItemsDatabase
import com.example.myapplication.network.FinHubApi
import com.example.myapplication.network.StockCompanyApi
import com.yaroslavgamayunov.stockviewer.vo.StockCompany
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
                pageSize = 20,

            ),
            remoteMediator = PageKeyedRemoteMediator(
                database,
                stockCompanyApi,
                finHubApi,
            ),
            pagingSourceFactory = { database.stockItemDao().getAll() }
        ).flow
    }

}
