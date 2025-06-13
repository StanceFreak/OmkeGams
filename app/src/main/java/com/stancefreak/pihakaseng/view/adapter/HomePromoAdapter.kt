package com.stancefreak.pihakaseng.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.stancefreak.pihakaseng.databinding.ItemListPromoBinding
import com.stancefreak.pihakaseng.model.remote.response.Result

class HomePromoAdapter: RecyclerView.Adapter<HomePromoAdapter.ViewHolder>() {

    private val promoData = ArrayList<Result>()

    inner class ViewHolder(private val binding: ItemListPromoBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Result) {
            Glide.with(binding.ivItemPromoThumb.context)
                .load("https://image.tmdb.org/t/p/w500${item.backdropPath}")
                .into(binding.ivItemPromoThumb)
//            binding.ivItemPromoThumb.load("https://image.tmdb.org/t/p/w500${item.backdropPath}")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = ItemListPromoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val holderData = this.promoData[position % promoData.size]
        holder.bind(holderData)
    }

    fun setData(dataList: List<Result>) {
        promoData.apply {
            clear()
            addAll(dataList)
        }
        notifyDataSetChanged()
    }
}