package com.example.myapplication.ui.stock

import com.example.myapplication.model.CompanyItem
import com.example.myapplication.model.NewsItem
import com.example.myapplication.model.StockCandleData
import com.example.myapplication.network.FinHubApi
import com.example.myapplication.network.NetworkResult
import com.example.myapplication.network.StockCompanyApi
import com.example.myapplication.network.apiCall
import java.text.SimpleDateFormat
import java.util.*

class
StockRepository(
    private val  finHubAli: FinHubApi,
    private val stockCompanyApi: StockCompanyApi,
) {

    suspend fun getCandleData(
        symbol: String,
        duration: StockDuration
    ): NetworkResult<StockCandleData> {
        return apiCall {
            finHubAli.getCandleData(
                duration.startTime,
                duration.endTime,
                duration.resolution,
                symbol
            )
        }
    }

    suspend fun getNews(
        symbol: String,
        startTime: Long,
        endTime: Long
    ): NetworkResult<List<NewsItem>> {
        val startDate = SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Date(startTime))
        val endDate = SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Date(endTime))
        return apiCall {
            finHubAli.getNews(symbol, startDate, endDate)
        }
    }

}

sealed class StockDuration {

    val endTime: Long
        get() {
            val calendar = Calendar.getInstance()
            return calendar.time.time.div(1000)
        }
    val startTime: Long
        get() {
            val calendar = Calendar.getInstance()
            when (this) {
                is All -> return 0
                is Day -> calendar.add(Calendar.DAY_OF_YEAR, -1)
                is HalfYear -> calendar.add(Calendar.MONTH, -6)
                is Month -> calendar.add(Calendar.MONTH, -1)
                is Week -> calendar.add(Calendar.WEEK_OF_YEAR, -1)
                is Year -> calendar.add(Calendar.YEAR, -1)
            }
            return calendar.time.time.div(1000)
        }

    val resolution: String
        get() {
            return when (this) {
                is All -> "W"
                is Year -> "D"
                is HalfYear -> "D"
                is Month -> "D"
                is Week -> "60"
                is Day -> "30"
            }
        }

    object Week : StockDuration()

    object Day : StockDuration()

    object HalfYear : StockDuration()

    object Month : StockDuration()

    object All : StockDuration()

    object Year : StockDuration()

}
