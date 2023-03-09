package com.example.myapplication.di

import com.example.myapplication.RepositoryScope
import com.example.myapplication.ui.companylist.CompanyListFragment
import dagger.Subcomponent


@Subcomponent(modules =
[RoomDBModule::class,NetworkApiModule::class])
@RepositoryScope
interface FragmentComponent {

    fun inject(companyListFragment: CompanyListFragment)


    @Subcomponent.Builder
    interface ModuleBuilder{
        fun roomModule(module: RoomDBModule): ModuleBuilder
        fun build(): FragmentComponent
        fun networkModule(module: NetworkApiModule):ModuleBuilder
    }
}



