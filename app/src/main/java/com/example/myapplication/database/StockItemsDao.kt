package com.example.myapplication.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yaroslavgamayunov.stockviewer.vo.StockCompany


@Dao
interface StockItemsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<StockCompany>): List<Long>

    @Query("SELECT * FROM stock_companies ORDER BY symbol ASC")
    fun getAll(): PagingSource<Int, StockCompany>

    @Query("SELECT * FROM stock_companies WHERE symbol=:ticker")
    suspend fun getStockItem(ticker: String): StockCompany

    @Query("SELECT * FROM stock_companies WHERE isFavourite=1 ORDER BY symbol ASC")
    fun getFavourites(): PagingSource<Int, StockCompany>

    @Query("UPDATE stock_companies SET isFavourite=1 WHERE symbol IN (:symbols)")
    suspend fun favourite(symbols: List<String>)

    @Query("UPDATE stock_companies SET isFavourite=0 WHERE symbol IN (:symbols)")
    suspend fun unFavourite(symbols: List<String>)

    @Query("DELETE FROM stock_companies")
    suspend fun clearStockItems(): Int

    @Query("SELECT * FROM stock_companies WHERE symbol LIKE :query OR name LIKE :query ORDER BY symbol ASC")
    fun searchCompany(query: String): PagingSource<Int, StockCompany>

    @Query("SELECT symbol FROM stock_companies ORDER BY (latestPrice - previousDayClosePrice) DESC LIMIT :n")
    suspend fun popularSymbols(n: Int): List<String>
}