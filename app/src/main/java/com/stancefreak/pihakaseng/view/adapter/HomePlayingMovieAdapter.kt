package com.stancefreak.pihakaseng.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.stancefreak.pihakaseng.databinding.ItemListNowPlayingBinding
import com.stancefreak.pihakaseng.databinding.ItemListPromoBinding
import com.stancefreak.pihakaseng.model.remote.response.Result

class HomePlayingMovieAdapter: RecyclerView.Adapter<HomePlayingMovieAdapter.ViewHolder>() {

    private val movieData = ArrayList<Result>()

    inner class ViewHolder(private val binding: ItemListNowPlayingBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Result) {
            Glide.with(binding.ivItemMoviePlayingThumb.context)
                .load("https://image.tmdb.org/t/p/w500${item.posterPath}")
                .into(binding.ivItemMoviePlayingThumb)
//            binding.ivItemPromoThumb.load("https://image.tmdb.org/t/p/w500${item.backdropPath}")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = ItemListNowPlayingBinding.inflate(
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
        val holderData = this.movieData[position % movieData.size]
        holder.bind(holderData)
    }

    fun setData(dataList: List<Result>) {
        movieData.apply {
            clear()
            addAll(dataList)
        }
        notifyDataSetChanged()
    }
}