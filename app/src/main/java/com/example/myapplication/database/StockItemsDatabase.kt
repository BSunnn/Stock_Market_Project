package com.example.myapplication.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.model.RemoteKeys
import com.example.myapplication.model.SavedStockItem
import com.example.myapplication.model.StockCompany


@Database(
    entities = [StockCompany::class, SavedStockItem::class, RemoteKeys::class],
    version = 1,
    exportSchema = false
)
abstract  class StockItemsDatabase: RoomDatabase() {
    abstract fun remoteKeysDao(): AccessKeysDao
    abstract fun savedDao(): SavedStockCompanyItemsDao
    abstract fun stockItemDao(): StockItemsDao

    companion object {
        const val DATABASE_NAME = "stock_items.database"
    }

}