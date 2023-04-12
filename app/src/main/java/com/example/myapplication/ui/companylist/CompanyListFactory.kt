package com.example.myapplication.ui.companylist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.database.StockItemsDatabase
import com.example.myapplication.network.FinHubApi
import com.example.myapplication.network.StockCompanyApi
import com.example.myapplication.ui.stock.StockRepository
import com.example.myapplication.ui.stock.StockViewModel
import javax.inject.Inject



//Factory is to create instance of ViewModel
class CompanyListFactory @Inject constructor(
    finHubAli: FinHubApi,
    stockCompanyApi: StockCompanyApi,
    database: StockItemsDatabase
) : ViewModelProvider.Factory {

    private val companyListRepository = CompanyListRepository(finHubAli, stockCompanyApi, database)

    private val stockApiRepository =
        StockRepository(finHubAli,stockCompanyApi)
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        // user 2 source of API and data into repository as parameter of viewModel
        if (modelClass.isAssignableFrom(CompanyListViewModel::class.java)) {
            return CompanyListViewModel(companyListRepository) as T
        }

        if (modelClass.isAssignableFrom(StockViewModel::class.java)) {
            return StockViewModel(stockApiRepository) as T
        }
        throw java.lang.IllegalArgumentException("view model illegal")
    }
}
