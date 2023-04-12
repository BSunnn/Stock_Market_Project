package com.example.myapplication.ui.companylist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.myapplication.model.StockCompany
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch

class CompanyListViewModel(private val companyListRepository: CompanyListRepository) : ViewModel() {

    fun getSavedStocksPaged(): Flow<PagingData<StockCompany>> {
        return companyListRepository.savedItems().cachedIn(viewModelScope)
    }

    suspend fun getStockItem(symbol: String): StockCompany =  companyListRepository.getStockItem(symbol)

    fun save(symbol: String) {
        viewModelScope.launch(Dispatchers.IO) {
            companyListRepository.saved(symbol)
        }
    }

    fun unSave(symbol: String) {
        viewModelScope.launch(Dispatchers.IO) {
            companyListRepository.unSave(symbol)
        }
    }

    suspend fun isSave(symbol: String)  = companyListRepository.isSaved(symbol)

}