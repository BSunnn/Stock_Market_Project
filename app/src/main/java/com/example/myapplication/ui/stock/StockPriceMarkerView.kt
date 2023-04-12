package com.example.myapplication.ui.stock

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import com.example.myapplication.databinding.MarkerStockChartBinding
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToLong

class StockPriceMarkerView(
    context: Context,
    layoutResource: Int,
    var highTimeRes: Boolean = false
) :
    MarkerView(context, layoutResource) {
    private val binding: MarkerStockChartBinding = MarkerStockChartBinding.inflate(
        LayoutInflater.from(context),
        this,
        true
    )

    var currentprice = 0

     var currentEntry: Entry? = null

    @SuppressLint("SetTextI18n")
    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        e?.let {
            currentEntry = it
            binding.stockPriceTextView.text = "$${it.y}"
            val time = (it.x.toDouble() * 1000).roundToLong()
            val timeFormat = if (highTimeRes) "d MMM yyyy HH:mm" else "d MMM yyyy"
            binding.stockPriceDateTextView.text = SimpleDateFormat(timeFormat, Locale.US).format(Date(time))

            currentprice= it.y.toInt()
        }
        super.refreshContent(e, highlight)
    }

    override fun getOffset(): MPPointF {
        return MPPointF((-(width / 2)).toFloat(), (-height - 20).toFloat())
    }
}