package com.example.myapplication.ui.stock

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class NameListAdapter(onClick: () -> Unit) : ListAdapter<String, NameViewHolder>(ITEM_COMPARATOR) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NameViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: NameViewHolder, position: Int) {
    }
    companion object {
        val ITEM_COMPARATOR = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String) = oldItem == newItem

            override fun areContentsTheSame(oldItem: String, newItem: String) = oldItem == newItem
        }
    }

}

class NameViewHolder(view:View): RecyclerView.ViewHolder(view) {

}
