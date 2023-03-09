package com.example.myapplication.ui.companylist

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.MainApplication
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentCompanyListBinding
import com.example.myapplication.databinding.FragmentLoginBinding
import com.example.myapplication.ui.home.StockListFilter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class CompanyListFragment : Fragment() {

    companion object {
        const val FILTER_LIST = "filter_list"
        fun newInstance(stockListFilter: StockListFilter): CompanyListFragment {
            return CompanyListFragment().apply {
                arguments = bundleOf(FILTER_LIST to stockListFilter)
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

        val filter = arguments?.get(FILTER_LIST)


        recyclerView.adapter = adapter

        lifecycleScope.launch {
            when (filter) {
                StockListFilter.ALL ->
                    viewModel.getStocksForIndexPaged("^NDX").collectLatest {
                        adapter.submitData(it)
                    }
            }
        }
    }

}