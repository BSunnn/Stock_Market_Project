package com.example.myapplication.ui.stock

import androidx.lifecycle.ViewModel
import com.example.myapplication.model.CompanyItem
import com.example.myapplication.model.NewsItem
import com.example.myapplication.model.StockCandleData
import com.example.myapplication.network.NetworkResult

class StockViewModel (private val repository: StockRepository): ViewModel(){

    suspend fun getNews(
        symbol: String,
        startTime: Long,
        endTime: Long
    ): NetworkResult<List<NewsItem>> {
        return repository.getNews(symbol, startTime, endTime)
    }

    suspend fun getCandleData(
        symbol: String,
        duration: StockDuration
    ): NetworkResult<StockCandleData> {
        return repository.getCandleData(symbol, duration)
    }

}
