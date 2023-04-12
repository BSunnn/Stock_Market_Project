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

//{
//    "c": [
//    217.68,
//    221.03,
//    219.89
//    ],
//    "h": [
//    222.49,
//    221.5,
//    220.94
//    ],
//    "l": [
//    217.19,
//    217.1402,
//    218.83
//    ],
//    "o": [
//    221.03,
//    218.55,
//    220
//    ],
//    "s": "ok",
//    "t": [
//    1569297600,
//    1569384000,
//    1569470400
//    ],
//    "v": [
//    33463820,
//    24018876,
//    20730608
//    ]
//}
