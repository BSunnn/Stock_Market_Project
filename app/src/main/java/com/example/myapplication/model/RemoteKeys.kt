package com.yaroslavgamayunov.stockviewer.vo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_access_keys")
data class RemoteKeys(
    @PrimaryKey
    val symbol: String,
    val currentKey: Int?,
    val prevKey: Int?,
    val nextKey: Int?
)