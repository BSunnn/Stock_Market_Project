package com.example.myapplication.model

import com.google.gson.annotations.SerializedName


data class NewsItem(
    val id: Int,
    @SerializedName("url")
    val newsUrl: String,
    @SerializedName("datetime")
    val date: Long,
    @SerializedName("image")
    val imageUrl: String,
    val source: String,
    val summary: String,
    val headline: String,
)

//[
//{
//    "category": "technology",
//    "datetime": 1596589501,
//    "headline": "Square surges after reporting 64% jump in revenue, more customers using Cash App",
//    "id": 5085164,
//    "image": "https://image.cnbcfm.com/api/v1/image/105569283-1542050972462rts25mct.jpg?v=1542051069",
//    "related": "",
//    "source": "CNBC",
//    "summary": "Shares of Square soared on Tuesday evening after posting better-than-expected quarterly results and strong growth in its consumer payments app.",
//    "url": "https://www.cnbc.com/2020/08/04/square-sq-earnings-q2-2020.html"
//}
//        ]