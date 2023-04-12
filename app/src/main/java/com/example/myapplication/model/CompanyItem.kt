package com.example.myapplication.model

import com.google.gson.annotations.SerializedName

data class CompanyItem(
    val symbol: String,
    val companyName: String,
    val industry: String,
    val exchange: String,
    val longDescription: String,
    val website: String,
    @SerializedName("CEO")
    val ceo: String,
    val sector: String,
    val primarySicCode: String?,
    val securityName: String,
    val employees: Long,
    val address: String?,
    val city: String?,
    val tags: List<String>,
    val issueType: String,
    val state: String?,
    val country: String?,
    val phone: String?,
    val zip: String?
) {
    fun getAddressString(): String {
        if (country == null || city == null || address == null) {
            return ""
        }
        return "$address, $city, $country"
    }

}

//"AAPL": {
//    "logo": {
//        "url": "https://storage.googleapis.com/iexcloud-hl37opg/api/logos/AAPL.png"
//    },
//    "quote": {
//        "avgTotalVolume": 59759242,
//        "calculationPrice": "iexlasttrade",
//        "change": -2.33,
//        "changePercent": -0.01524,
//        "close": null,
//        "closeSource": "official",
//        "closeTime": null,
//        "companyShort": "Apple Inc",
//        "currency": "USD",
//        "delayedPrice": null,
//        "delayedPriceTime": null,
//        "extendedChange": null,
//        "extendedChangePercent": null,
//        "extendedPrice": null,
//        "extendedPriceTime": null,
//        "high": null,
//        "highSource": null,
//        "highTime": null,
//        "iexAskPrice": 0,
//        "iexAskSize": 0,
//        "iexBidPrice": 0,
//        "iexBidSize": 0,
//        "iexClose": 150.54,
//        "iexCloseTime": 1678395599900,
//        "iexLastUpdated": 1678395599900,
//        "iexMarketPercent": 0.013172356969458891,
//        "iexOpen": 153.515,
//        "iexOpenTime": 1678372200402,
//        "iexRealtimePrice": 150.54,
//        "iexRealtimeSize": 367,
//        "iexVolume": 708431,
//        "lastTradeTime": 1678395599942,
//        "latestPrice": 150.54,
//        "latestSource": "IEX Last Trade",
//        "latestTime": "March 9, 2023",
//        "latestUpdate": 1678395599900,
//        "latestVolume": null,
//        "low": null,
//        "lowSource": null,
//        "lowTime": null,
//        "marketCap": 2381835750840,
//        "oddLotDelayedPrice": null,
//        "oddLotDelayedPriceTime": null,
//        "open": null,
//        "openTime": null,
//        "openSource": "official",
//        "peRatio": 25.6,
//        "previousClose": 152.87,
//        "previousVolume": 47204791,
//        "primaryExchange": "NASDAQ",
//        "symbol": "AAPL",
//        "volume": null,
//        "week52High": 178.3,
//        "week52Low": 123.98,
//        "ytdChange": 0.1489301077375728,
//        "isUSMarketOpen": false
//    }
//}