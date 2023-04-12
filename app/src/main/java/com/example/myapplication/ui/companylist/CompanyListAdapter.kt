package com.example.myapplication.ui.companylist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.myapplication.R
import com.example.myapplication.databinding.CompanyItemBinding
import com.example.myapplication.model.StockCompany


class CompanyListAdapter(val onItemClick: (StockCompany) -> Unit) :
    RecyclerView.Adapter<ViewHolder>() {

    var items: List<StockCompany> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CompanyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = ViewHolder(binding)

        viewHolder.onClick { adapterPosition ->
            onItemClick(items[adapterPosition])
        }
        return viewHolder
    }
}

class ViewHolder(private val binding: CompanyItemBinding) : RecyclerView.ViewHolder(binding.root){

    fun onClick(listener: (Int) -> Unit) {
        itemView.setOnClickListener {
            listener.invoke(bindingAdapterPosition)
        }
    }

    fun bind(item:StockCompany?){
        if (item != null){
            binding.companyShort.text = item.symbol
            binding.companyName.text = item.name

            binding.stockPrice.text = item.previousDayClosePrice.toString()
            val buttonDrawableRes = if(item.isSaved)
                R.drawable.baseline_favorite_saved else R.drawable.baseline_favorite
            binding.favouriteImage.setImageResource(buttonDrawableRes)

            item.logoUrl?.let { bindImage(it) }

        } else {
            binding.companyShort.text = ""
            binding.companyName.text = ""
        }
    }

    private fun bindImage(logoUrl: String) {
        val cornerRadius = 12.0f
        binding.favouriteImage.load(logoUrl) {
            placeholder(R.drawable.corners_round_search)
            transformations(RoundedCornersTransformation(cornerRadius))
            crossfade(true)
        }
    }
}
