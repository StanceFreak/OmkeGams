package com.stancefreak.pihakaseng.view.adapter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.stancefreak.pihakaseng.R
import com.stancefreak.pihakaseng.databinding.ItemListNowPlayingBinding
import com.stancefreak.pihakaseng.model.remote.response.Result
import com.stancefreak.pihakaseng.view.home.HomeViewModel

class HomePlayingMovieAdapter(
    private val vm: HomeViewModel
): RecyclerView.Adapter<HomePlayingMovieAdapter.ViewHolder>() {

    private val movieData = ArrayList<Result>()
    inner class ViewHolder(private val binding: ItemListNowPlayingBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Result) {
            binding.apply {
                Glide.with(ivItemMoviePlayingThumb.context)
                    .load("https://image.tmdb.org/t/p/w500${item.posterPath}")
                    .into(ivItemMoviePlayingThumb)
//                tvItemMoviePlayingIndex.text = ((adapterPosition % 20) + 1).toString()
                tvItemMoviePlayingIndex.text = (((adapterPosition + movieData.size - 2) % movieData.size) + 1).toString()
                itemView.setOnClickListener {
                    val bundle = Bundle()
                    bundle.putInt("movieId", item.id)
                    vm.storeVpState(adapterPosition)
                    itemView.findNavController().navigate(R.id.home_to_detail, bundle)
                }
            }
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
//        return Int.MAX_VALUE
        return movieData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val holderData = this.movieData[position % movieData.size]
        Log.d("test item size :", movieData.size.toString())
        val holderData = this.movieData[position]
        holder.bind(holderData)
    }

    fun setData(dataList: List<Result>) {
        val itemSize = dataList.size
        movieData.apply {
            clear()
            for (i in 0 until 22) {
                add(dataList[(i + itemSize - 2) % itemSize])
            }
//            add(dataList[dataList.size - 1])
//            addAll(dataList)
//            add(dataList[1])
        }
        notifyDataSetChanged()
    }
}