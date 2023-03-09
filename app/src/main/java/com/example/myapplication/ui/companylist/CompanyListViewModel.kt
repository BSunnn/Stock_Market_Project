package com.example.myapplication.ui.companylist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.yaroslavgamayunov.stockviewer.vo.StockCompany
import kotlinx.coroutines.flow.Flow

class CompanyListViewModel(private val companyListRepository: CompanyListRepository) : ViewModel() {

    fun getStocksForIndexPaged(stockIndex: String): Flow<PagingData<StockCompany>> {
        return companyListRepository.allItems(stockIndex).cachedIn(viewModelScope)
    }
}