package com.example.myapplication.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.ui.companylist.CompanyListFragment
import com.google.android.material.tabs.TabLayoutMediator


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val viewPager = binding.viewPager
        val tab = binding.tableLayout
        val adapter = ViewPageAdapter(requireActivity())
        viewPager.adapter = adapter

        val tabTitles = requireActivity().resources.getStringArray(R.array.tab_titles)
        TabLayoutMediator(tab, viewPager) { tab, position ->
            tab.text = tabTitles[position]
            viewPager.setCurrentItem(tab.position, true)
        }.attach()
        viewPager.isUserInputEnabled = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

enum class StockListFilter {
    ALL, SAVED
}


class ViewPageAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount() = StockListFilter.values().size

    override fun createFragment(position: Int) = CompanyListFragment.newInstance(StockListFilter.values()[position])

}