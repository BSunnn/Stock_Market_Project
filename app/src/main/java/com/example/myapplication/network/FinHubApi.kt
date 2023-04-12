package com.example.myapplication.network

import com.example.myapplication.model.ConstituentsResponse
import com.example.myapplication.model.NewsItem
import com.example.myapplication.model.StockCandleData
import retrofit2.http.GET
import retrofit2.http.Query

interface FinHubApi {
    companion object {
        const val BASE_URL = "https://finnhub.io/api/v1/"
    }
    @GET("index/constituents?")
    suspend fun getConstituents(
        @Query("symbol") stockIndex: String,
    ): ConstituentsResponse

    @GET("company-news")
    suspend fun getNews(
        @Query("symbol") symbol: String,
        @Query("from") from: String,
        @Query("to") to: String
    ): List<NewsItem>

    @GET("stock/candle")
    suspend fun getCandleData(
        @Query("from") from: Long,
        @Query("to") to: Long,
        @Query("resolution") resolution: String,
        @Query("symbol") symbol: String,
    ): StockCandleData?

}