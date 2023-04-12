package com.example.myapplication.ui.companylist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.MainApplication
import com.example.myapplication.databinding.FragmentCompanyListBinding
import com.example.myapplication.model.StockCompany
import com.example.myapplication.ui.home.HomeFragmentDirections
import com.example.myapplication.ui.home.StockListFilter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class CompanyListFragment : Fragment() {

    companion object {
        private const val LIST_FILTER_TAG = "filter_list"

        fun newInstance(stockListFilter: StockListFilter): CompanyListFragment {
            return CompanyListFragment().apply {
                arguments = bundleOf(LIST_FILTER_TAG to stockListFilter)
            }
        }
    }

    private var _binding: FragmentCompanyListBinding? = null

    @Inject
    lateinit var companyListFactory: CompanyListFactory
    lateinit var viewModel: CompanyListViewModel

    lateinit var recyclerView: RecyclerView
    lateinit var adapter: CompanyListAdapter

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCompanyListBinding.inflate(inflater, container, false)
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (requireActivity().application as MainApplication).fragmentComponent.inject(this)
        viewModel = ViewModelProvider(
            requireActivity(),
            companyListFactory
        )[CompanyListViewModel::class.java]

        val filter = arguments?.get(LIST_FILTER_TAG)?: StockListFilter.ALL

        adapter = CompanyListAdapter(
            onItemClick = { compnayItems->
                val action = HomeFragmentDirections.actionNavigationHomeToNavigationStock(compnayItems.symbol)
                findNavController().navigate(action)
            }
        )

        recyclerView.adapter = adapter

        val stockCompanyList = readJsonFileFromAssets()

        when (filter) {
            StockListFilter.ALL -> {
                adapter.items = stockCompanyList.map { stockCompany ->
                    StockCompany(
                        symbol = stockCompany.symbol,
                        name = stockCompany.name,
                        previousDayClosePrice = stockCompany.previousDayClosePrice,
                        logoUrl = stockCompany.logoUrl,
                        isSaved = stockCompany.isSaved,
                        latestPrice = stockCompany.latestPrice
                    )
                }
            }
            StockListFilter.SAVED -> {
                lifecycleScope.launch {
                    viewModel.getSavedStocksPaged().collectLatest {
                        adapter.items = stockCompanyList.filter { stockCompany->
                            stockCompany.isSaved
                        }.map {stockCompany ->
                            StockCompany(
                                symbol = stockCompany.symbol,
                                name = stockCompany.name,
                                previousDayClosePrice = stockCompany.previousDayClosePrice,
                                logoUrl = stockCompany.logoUrl,
                                isSaved = stockCompany.isSaved,
                                latestPrice = stockCompany.latestPrice
                            )
                        }

                    }

                }
            }
        }
    }


    private   fun readJsonFileFromAssets(): List<StockCompany> {
        val json = context?.assets?.open("stocks.json")?.bufferedReader().use { it?.readText() }
        val type = object : TypeToken<List<StockCompany>>() {}.type
        return Gson().fromJson(json, type)
    }
}