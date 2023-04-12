package com.example.myapplication.database

import androidx.room.*
import com.example.myapplication.model.SavedStockItem

@Dao
interface SavedStockCompanyItemsDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(item: SavedStockItem)

    @Delete
    suspend fun unSaved(item: SavedStockItem)

    @Query("SELECT EXISTS (SELECT 1 FROM saved_stock_companies WHERE symbol=:ticker)")
    suspend fun isSaved(ticker: String): Int

    @Query("SELECT * FROM saved_stock_companies WHERE symbol IN (:symbols)")
    suspend fun filterSaved(symbols: List<String>): List<SavedStockItem>

    @Query("DELETE FROM saved_stock_companies")
    suspend fun clearFavourites()

}