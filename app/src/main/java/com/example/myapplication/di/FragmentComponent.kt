package com.example.myapplication.di

import com.example.myapplication.RepositoryScope
import com.example.myapplication.ui.companylist.CompanyListFragment
import com.example.myapplication.ui.stock.ChartFragment
import com.example.myapplication.ui.stock.CompanyDetailFragment
import com.example.myapplication.ui.stock.CompanyNewsFragment
import com.example.myapplication.ui.stock.StockFragment
import dagger.Subcomponent



// FragmentComponent is a subcomponent
// of AppComponent that provides dependencies specifically for fragments in the application
@Subcomponent(modules = [RoomDBModule::class,NetworkApiModule::class])
@RepositoryScope
interface FragmentComponent {

    fun inject(companyListFragment: CompanyListFragment)

    fun inject(stockFragment: StockFragment)
    fun inject(companyDetailFragment: CompanyDetailFragment)
    fun inject(companyNewsFragment: CompanyNewsFragment)
    fun inject(chartFragment: ChartFragment)

    @Subcomponent.Builder
    interface ModuleBuilder{
        fun roomModule(module: RoomDBModule): ModuleBuilder
        fun build(): FragmentComponent
        fun networkModule(module: NetworkApiModule):ModuleBuilder
    }
}



