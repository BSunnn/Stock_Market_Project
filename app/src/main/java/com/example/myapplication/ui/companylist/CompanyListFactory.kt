package com.example.myapplication.ui.companylist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.database.StockItemsDatabase
import com.example.myapplication.network.FinHubApi
import com.example.myapplication.network.StockCompanyApi
import javax.inject.Inject

class CompanyListFactory @Inject constructor(
    finHubAli: FinHubApi,
    stockCompanyApi: StockCompanyApi,
    database: StockItemsDatabase
) : ViewModelProvider.Factory {

    private val companyListRepository = CompanyListRepository(finHubAli, stockCompanyApi, database)


    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(CompanyListViewModel::class.java)) {
            return CompanyListViewModel(companyListRepository) as T
        }
        throw java.lang.IllegalArgumentException("view model illegal")
    }
}
