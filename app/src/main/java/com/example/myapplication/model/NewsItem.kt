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