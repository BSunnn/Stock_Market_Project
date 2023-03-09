package com.example.myapplication.network


import com.example.myapplication.model.CompanyItem
import com.example.myapplication.model.StockCompany

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface StockCompanyApi {

    companion object Factory {
        const val BASE_URL = "https://cloud.iexapis.com/v1/"
    }

    @GET("stock/market/batch?types=quote,logo")
    suspend fun getStockItems(
        @Query("symbols", encoded = true) tickers: String,
    ): List<CompanyItem>

    @GET("/stable/stock/{symbol}/company")
    suspend fun getCompanyItem(@Path("symbol") ticker: String): StockCompany
}