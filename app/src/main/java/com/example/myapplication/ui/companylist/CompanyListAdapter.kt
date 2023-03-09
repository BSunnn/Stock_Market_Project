package com.example.myapplication.ui.companylist

import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.model.StockCompany

class CompanyListAdapter(
    val onItemClick: (StockCompany) -> Unit
):  PagingDataAdapter<StockCompany, ViewHolder>(ItemComparator) {





    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
    }


}

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

}



object ItemComparator : DiffUtil.ItemCallback<StockCompany>() {
    override fun areItemsTheSame(oldItem: StockCompany, newItem: StockCompany): Boolean {
        return oldItem.symbol == oldItem.symbol
    }

    override fun areContentsTheSame(oldItem: StockCompany, newItem: StockCompany): Boolean {
        return oldItem == newItem
    }

}
