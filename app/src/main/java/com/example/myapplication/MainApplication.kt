package com.example.myapplication

import android.app.Application
import com.example.myapplication.di.AppComponent
import com.example.myapplication.di.AppModule
import com.example.myapplication.di.DaggerAppComponent
import com.example.myapplication.di.FragmentComponent
import com.yandex.metrica.YandexMetricaConfig

class MainApplication: Application() {

    lateinit var component: AppComponent

    val fragmentComponent: FragmentComponent by lazy {
        component.repositoryComponentBuilder().build()
    }
    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .build()
    }
}