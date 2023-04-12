package com.example.myapplication.di

import android.app.Application
import android.content.Context
import com.example.myapplication.ApplicationScope
import com.example.myapplication.MainApplication
import dagger.Module
import dagger.Provides
import javax.inject.Named


@Module
class AppModule(private val application: MainApplication ) {

    //Base class for maintaining global application state
    @Named("application")
    @Provides
    @ApplicationScope
    fun provideMainApplication(): Application {
        return application
    }

    @Named("appContext")
    @Provides
    @ApplicationScope
    fun provideContext(@Named("application") application: Application): Context {
        return application.applicationContext
    }
}