package com.example.myapplication.model

import com.google.gson.annotations.SerializedName

data class ConstituentsResponse(
    @SerializedName("symbol")
    val index: String,
    @SerializedName("constituents")
    val symbol: List<String>

)