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
import com.example.myapplication.databinding.FragmentCompanyNewsBinding
import com.example.myapplication.network.NetworkResult
import com.example.myapplication.ui.companylist.CompanyListFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

class CompanyNewsFragment : Fragment() {

    private var _binding : FragmentCompanyNewsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var companyListFactory: CompanyListFactory
    lateinit var viewModel: StockViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCompanyNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (requireActivity().application as MainApplication)
            .fragmentComponent.inject(this)

        viewModel = ViewModelProvider(requireActivity(),companyListFactory)[StockViewModel::class.java]

        val adapter = NewsAdapter()
        binding.newsRecyclerview.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {

            val symbol = requireArguments().getString(STOCK_TICKER_TAG)!!
            val calendar = Calendar.getInstance()
            val endTime = calendar.time.time
            calendar.add(Calendar.WEEK_OF_YEAR, -1)
            val startTime = calendar.time.time
            val news = viewModel.getNews(symbol, startTime, endTime)
            withContext(Dispatchers.Main) {
                if (news is NetworkResult.Success){
                    adapter.submitList(news.value)
                }
            }
        }
    }

    companion object {
        private const val STOCK_TICKER_TAG = "stock_ticker"

        fun newInstance(ticker: String): CompanyNewsFragment {
            return CompanyNewsFragment().apply {
                arguments = bundleOf(STOCK_TICKER_TAG to ticker)
            }
        }
    }
}