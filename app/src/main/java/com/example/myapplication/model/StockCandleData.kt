package com.example.myapplication.model

import com.google.gson.annotations.SerializedName

data class StockCandleData(
    @SerializedName("o")
    val openPrices: List<Double>,

    //for high price
    @SerializedName("h")
    val highPrices: List<Double>,
    //for low price
    @SerializedName("l")
    val lowPrices: List<Double>,
    //current price
    @SerializedName("c")
    val closePrices: List<Double>,
    @SerializedName("t")
    val timeStamps: List<Long>
)
