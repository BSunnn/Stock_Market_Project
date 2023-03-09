package com.yaroslavgamayunov.stockviewer.vo

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * This table is made to be able to persist user's favourite items, because api has no support
 * of favourite stock items
 */
@Entity(tableName = "saved_stock_companies")
data class SavedStockItem(@PrimaryKey val symbol: String)