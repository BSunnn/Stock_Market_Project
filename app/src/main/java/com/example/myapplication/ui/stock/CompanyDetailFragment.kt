package com.example.myapplication.ui.stock

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.MainApplication
import com.example.myapplication.databinding.FragmentCompanyDetailBinding
import com.example.myapplication.model.CompanyItem
import com.example.myapplication.network.NetworkResult
import com.example.myapplication.ui.companylist.CompanyListFactory
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.yandex.metrica.impl.ob.it
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CompanyDetailFragment : Fragment() {

    @Inject
    lateinit var stockViewModelFactory: CompanyListFactory

    private lateinit var stockApiViewModel: StockViewModel

    private var _binding: FragmentCompanyDetailBinding? = null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCompanyDetailBinding.inflate(inflater, container, false)
        return binding.root

    }

    private fun getCompanyFromJson(symbol: String): CompanyItem? {
        val json = context?.assets?.open("companyNews.json")?.bufferedReader().use { it?.readText() }
        val companies = Gson().fromJson<List<CompanyItem>>(json, object : TypeToken<List<CompanyItem>>() {}.type)
        return companies.find { it.symbol == symbol }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (requireActivity().application as MainApplication)
            .fragmentComponent.inject(this)

        stockApiViewModel = ViewModelProvider(requireActivity(),stockViewModelFactory).get(StockViewModel::class.java)
        arguments?.getString(STOCK_TICKER_TAG)?.let { companyDetails(it) }
    }

    private fun companyDetails(symbol: String) {


          val companyInfo =getCompanyFromJson(symbol)
            if (companyInfo != null) {
                updateCompanyInfoLayout(companyInfo)
            }
        }


    private fun updateCompanyInfoLayout(companyItem: CompanyItem) {
        binding.companyName.text = companyItem.companyName
        binding.employeesTextView.text = companyItem.employees.toString()
        binding.addressTextView.apply {
            val address = companyItem.getAddressString()
            text = address
            visibility = if (address.isEmpty()) View.GONE else View.VISIBLE
        }
        binding.companyDescription.text = companyItem.longDescription
        with(binding.companyWebsiteTextView) {
            text = companyItem.website
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val STOCK_TICKER_TAG = "stock_ticker"

        fun newInstance(ticker: String): CompanyDetailFragment {
            return CompanyDetailFragment().apply {
                arguments = bundleOf(STOCK_TICKER_TAG to ticker)
            }
        }
    }
}