package com.example.myapplication.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.model.RemoteKeys

@Dao
interface AccessKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(constituents: List<RemoteKeys>)

    @Query("DELETE FROM remote_access_keys")
    suspend fun clearKeys(): Int

    @Query("SELECT * FROM remote_access_keys WHERE currentKey=:page")
    suspend fun remoteKeysByPage(page: Int): List<RemoteKeys>

    @Query("SELECT * FROM remote_access_keys WHERE symbol=:symbol")
    suspend fun remoteKeysBySymbol(symbol: String): RemoteKeys?
}