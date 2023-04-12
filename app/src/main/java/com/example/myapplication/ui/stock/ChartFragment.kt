package com.example.myapplication.ui.stock

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.MainApplication
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentChartBinding
import com.example.myapplication.model.StockCandleData
import com.example.myapplication.network.NetworkResult
import com.example.myapplication.ui.companylist.CompanyListFactory
import com.example.myapplication.ui.companylist.CompanyListViewModel
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.Chart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IFillFormatter
import com.google.android.material.color.MaterialColors
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


class ChartFragment : Fragment() {


    private var _binding :FragmentChartBinding? = null

    private val binding get() = _binding!!


    private lateinit var marker: StockPriceMarkerView


    @Inject
    lateinit var stockViewModelFactory: CompanyListFactory

    private lateinit var viewModel: CompanyListViewModel
    private lateinit var stockViewModel: StockViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (requireActivity().application as MainApplication).fragmentComponent.inject(this)
        viewModel =  ViewModelProvider(requireActivity(), stockViewModelFactory)[CompanyListViewModel::class.java]
        stockViewModel = ViewModelProvider(requireActivity(), stockViewModelFactory)[StockViewModel::class.java]
        plotChart()
    }

    private fun plotChart() {
        val chart = binding.stockDetailChart

        marker = StockPriceMarkerView(
            requireActivity(),
            R.layout.marker_stock_chart
        )

        marker.chartView = chart
        chart.marker = marker

        val symbol = requireArguments().getString(STOCK_TICKER_TAG)!!

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            //val currentPrice = viewModel.getStockItem(symbol).latestPrice
            val currentPrice = marker.currentEntry
            withContext(Dispatchers.Main) {
                //binding.stockChartCurrentPriceTextView.text =  "$currentPrice"
            }
        }


        with(chart){
            xAxis.isEnabled = false
            axisLeft.isEnabled = false
            axisRight.isEnabled = false
            description.isEnabled = false
            legend.isEnabled = false
            minOffset = 0f
        }

        val chartPaint = chart.getPaint(Chart.PAINT_INFO)
        chartPaint.textSize = 30f
        chartPaint.color = MaterialColors.getColor(chart, androidx.navigation.ui.R.attr.colorSecondary)
        val buttons = arrayOf(
            binding.stockChartDayButton,
            binding.stockChartWeekButton,
            binding.stockChartMonthButton,
            binding.stockChartHalfYearButton,
            binding.stockChartYearButton,
            binding.stockChartAllButton
        )

        val durations = arrayOf(
            StockDuration.Day,
            StockDuration.Week,
            StockDuration.Month,
            StockDuration.HalfYear,
            StockDuration.Year,
            StockDuration.All
        )

        buttons.forEachIndexed { index, button ->
            button.setOnClickListener {
                loadChartData(symbol, durations[index])
            }
        }
    }

    private fun loadChartData(symbol: String, stockDuration: StockDuration) {
        marker.highTimeRes = stockDuration is StockDuration.Day

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main){
                binding.progressBar.visibility = View.VISIBLE
                binding.stockDetailChart.visibility = View.INVISIBLE
            }

            when(val data = stockViewModel.getCandleData(symbol,stockDuration)) {
                is NetworkResult.Error -> { }

                is NetworkResult.Success -> {
                    withContext(Dispatchers.Main){
                        binding.progressBar.visibility = View.INVISIBLE
                        binding.stockDetailChart.visibility = View.VISIBLE
                        setupData(data.value)
                    }
                }
            }
        }
    }

    private fun setupData(data: StockCandleData?) {

        val chartView = binding.stockDetailChart

        if (data?.closePrices == null || data.timeStamps == null){
            chartView.clear()
            return
        }

        val entry = data.timeStamps.zip(data.closePrices).map {
            (timeStamps,closePrices)->Entry(timeStamps.toFloat(),closePrices.toFloat())
        }

        val dataset = LineDataSet(entry,"")

        val drawable = ContextCompat.getDrawable(requireActivity(),R.drawable.gradient_chart_background)

        with(dataset){
            setDrawFilled(true)
            fillFormatter = IFillFormatter { _, _ -> chartView.axisLeft.axisMinimum  }
            lineWidth =2f
            fillDrawable = drawable
            mode = LineDataSet.Mode.HORIZONTAL_BEZIER
            color = MaterialColors.getColor(chartView, androidx.navigation.ui.R.attr.colorSecondary)
            setDrawValues(false)
            setDrawCircles(false)
            highLightColor = MaterialColors.getColor(chartView, androidx.navigation.ui.R.attr.colorSecondary)
            setDrawHorizontalHighlightIndicator(false)
            enableDashedHighlightLine(15f, 5f, 0f)
            highlightLineWidth = 1.5f

        }
        with(chartView){
            minOffset = 0f
            extraTopOffset = 32f
            this.data = LineData(dataset).also { it.isHighlightEnabled = true }
            notifyDataSetChanged()
            animateY(2000, Easing.EaseOutBack)

        }
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }


    companion object {
        private const val STOCK_TICKER_TAG = "stock_ticker"

        fun newInstance(ticker: String): ChartFragment {
            return ChartFragment().apply {
                arguments = bundleOf(STOCK_TICKER_TAG to ticker)
            }
        }
    }
}