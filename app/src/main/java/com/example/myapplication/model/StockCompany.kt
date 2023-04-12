package com.example.myapplication.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.*
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import java.lang.reflect.Type

@Entity(tableName = "stock_companies")
@Serializable
data class StockCompany(
    @PrimaryKey
    @SerializedName("symbol")
    val symbol: String,
    @SerializedName("companyName")
    val name: String? = null,
    @SerializedName("previousClose")
    val previousDayClosePrice: Double? = null,
    val logoUrl: String?,
    var isSaved: Boolean = false,
    val latestPrice: Double
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