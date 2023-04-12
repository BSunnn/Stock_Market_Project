package com.example.myapplication.ui.stock

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class StockViewPagerAdapter(requireActivity: FragmentActivity, val symbol: String) :
    FragmentStateAdapter(requireActivity) {
    override fun getItemCount(): Int =StockTabType.values().size

    override fun createFragment(position: Int): Fragment {
        return when (StockTabType.values()[position]) {
            StockTabType.STOCK_CHART -> {
                ChartFragment.newInstance(symbol)
            }
            StockTabType.DETAILS -> {
                CompanyDetailFragment.newInstance(symbol)
            }
            StockTabType.NEWS -> {
                CompanyNewsFragment.newInstance(symbol)
            }
        }
    }

}

 enum class StockTabType {
     STOCK_CHART,DETAILS, NEWS
}
