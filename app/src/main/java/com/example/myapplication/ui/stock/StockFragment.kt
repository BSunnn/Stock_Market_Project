package com.example.myapplication.ui.stock

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myapplication.MainActivity
import com.example.myapplication.MainApplication
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentStockBinding
import com.example.myapplication.model.StockCompany
import com.example.myapplication.ui.companylist.CompanyListFactory
import com.example.myapplication.ui.companylist.CompanyListViewModel
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class StockFragment : Fragment() {

    private  var _binding: FragmentStockBinding? = null
    private val args: StockFragmentArgs by navArgs()

    @Inject
    lateinit var companyListFactory: CompanyListFactory
    lateinit var viewModel: CompanyListViewModel
    private val binding get() = _binding!!

    lateinit var toolbar: Toolbar

    private var isSaved: Boolean = false
       set(value) {

           val drawable = if (value) R.drawable.baseline_favorite_saved else R.drawable.baseline_favorite
           toolbar.menu.findItem(R.id.actionSaved).setIcon(drawable)

           if (value) {
               viewModel.save(args.ticker)
           } else {
               viewModel.unSave(args.ticker)
           }
           field = value
       }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStockBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as MainActivity).supportActionBar!!.hide()

        toolbar = binding.stockFragmentToolbar.apply {
            setNavigationOnClickListener {
                view.findNavController().navigateUp()
            }
            setOnMenuItemClickListener {
                return@setOnMenuItemClickListener when (it.itemId) {

                    R.id.actionSaved -> {

                        isSaved = !isSaved
                        updateIsSavd(requireContext(),args.ticker,isSaved)
                        Toast.makeText(requireContext(), "Its a toast!", Toast.LENGTH_SHORT).show()
                        true
                    }
                    else -> false
                }
            }
        }
    }




    fun updateIsSavd(context: Context, stockSymbol: String, isSaved: Boolean) {
        val jsonString = context.assets.open("stocks.json").bufferedReader().use { it.readText() }

        val json = Json { ignoreUnknownKeys = true }
        val stockCompanies = json.decodeFromString<List<StockCompany>>(jsonString)


        val updatedStockCompanies = stockCompanies.map { stockCompany ->
            if (stockCompany.symbol == stockSymbol) {
                stockCompany.copy(isSaved = isSaved)
            } else {
                stockCompany
            }
        }

        val updatedJsonString = json.encodeToString(updatedStockCompanies)

        // Save the updated JSON string to the file
        context.openFileOutput("stocks.json", Context.MODE_PRIVATE).use {
            it.write(updatedJsonString.toByteArray())
        }
        println(updatedJsonString)
    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (requireActivity().application as MainApplication).fragmentComponent.inject(this)

        viewModel = ViewModelProvider(
            requireActivity(),
            companyListFactory
        )[CompanyListViewModel::class.java]

        lifecycleScope.launchWhenCreated {
            isSaved = viewModel.isSave(args.ticker)
            val company = viewModel.getStockItem(args.ticker)
//            toolbar.subtitle = "company.name"
//            toolbar.title =company.symbol
        }

        val binding = FragmentStockBinding.bind(requireView())
        val viewPager = binding.viewPager
        val tabLayout = binding.tabLayout
        val adapter = StockViewPagerAdapter(requireActivity(), args.ticker)
        viewPager.adapter = adapter

        val tabTitles = requireActivity().resources.getStringArray(R.array.stock_tab_titles)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabTitles[position]
            viewPager.setCurrentItem(tab.position,true)
        }.attach()
        viewPager.isUserInputEnabled = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}