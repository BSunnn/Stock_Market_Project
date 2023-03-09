package com.yaroslavgamayunov.stockviewer.vo

import com.google.gson.annotations.SerializedName

data class ConstituentsResponse(
    @SerializedName("symbol")
    val index: String,
    @SerializedName("constituents")
    val symbol: List<String>

)