package com.example.myapplication.di

import android.content.Context
import androidx.room.Room
import com.example.myapplication.RepositoryScope
import com.example.myapplication.database.StockItemsDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Named
//providing an instance of the StockItemsDatabase inside the RepositoryScope
@Module
class RoomDBModule {
    @Provides
    @RepositoryScope
    fun provideStockDataBase(@Named("appContext") context: Context): StockItemsDatabase {
        return Room
            .databaseBuilder(context, StockItemsDatabase::class.java, StockItemsDatabase.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }
}
