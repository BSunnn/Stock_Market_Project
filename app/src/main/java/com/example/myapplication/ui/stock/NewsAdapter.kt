package com.example.myapplication.ui.stock

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.myapplication.R
import com.example.myapplication.databinding.NewsItemBinding
import com.example.myapplication.model.NewsItem
import java.text.SimpleDateFormat
import java.util.*

class NewsAdapter : ListAdapter<NewsItem, NewsAdapter.ViewHolder>(ITEM_COMPARATOR) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val binding = NewsItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object  {
        val ITEM_COMPARATOR = object: DiffUtil.ItemCallback<NewsItem>(){
            override fun areItemsTheSame(oldItem: NewsItem, newItem: NewsItem): Boolean = oldItem == newItem

            override fun areContentsTheSame(oldItem: NewsItem, newItem: NewsItem): Boolean = oldItem.id == newItem.id

        }
    }

    class ViewHolder(val binding: NewsItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: NewsItem?){
            if (item != null){
                binding.newsSource.text = item.source
                binding.newsHeadline.text = item.headline
                binding.newsSummary.text = item.summary
                binding.newsDate.text = SimpleDateFormat(
                    "EEE, d MMM HH:mm",
                    Locale.US
                ).format(Date(item.date*100))

                itemView.setOnClickListener {
                    val uri = Uri.parse(item.newsUrl)
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    startActivity(itemView.context, intent, null)
                }

            } else {
                binding.newsSource.text = "no data "
                binding.newsHeadline.text = "no data "
                binding.newsSummary.text = "no data "
            }
        }
    }
}