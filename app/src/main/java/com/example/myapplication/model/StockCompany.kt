package com.example.myapplication.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.*
import com.google.gson.annotations.SerializedName
import java.lang.reflect.Type

@Entity(tableName = "stock_companies")
data class StockCompany(
    @PrimaryKey
    @SerializedName("symbol")
    val symbol: String,
    @SerializedName("companyName")
    val name: String,
    @SerializedName("previousClose")
    val previousDayClosePrice: Double,
    val latestPrice: Double,
    val isFavourite: Boolean = false,
    val logoUrl: String
)

// process the Iex response
class StockItemListResponseDeserializer : JsonDeserializer<List<StockCompany>> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): List<StockCompany> {
        if (json == null) {
            return listOf()
        }

        val gson = Gson()
        val entries = json.asJsonObject.entrySet()

        return entries.map { (_, companyJson) ->
            val companyObject = companyJson.asJsonObject.deepCopy()
            val logoUrl = companyObject["logo"].asJsonObject["url"].asString
            companyObject["quote"].asJsonObject.add("logoUrl", JsonPrimitive(logoUrl))
            gson.fromJson(companyObject["quote"], StockCompany::class.java)
        }
    }
}