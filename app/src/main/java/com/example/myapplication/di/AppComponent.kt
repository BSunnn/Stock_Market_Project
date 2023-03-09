package com.example.myapplication.di

import com.example.myapplication.ApplicationScope
import dagger.Component

@Component(modules = [AppModule::class])
@ApplicationScope
interface AppComponent {
    fun repositoryComponentBuilder(): FragmentComponent.ModuleBuilder
}