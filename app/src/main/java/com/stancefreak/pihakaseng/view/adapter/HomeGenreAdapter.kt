package com.stancefreak.pihakaseng.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stancefreak.pihakaseng.databinding.ItemListGenreBinding
import com.stancefreak.pihakaseng.model.remote.response.Genre
import com.stancefreak.pihakaseng.model.remote.response.MoviesGenreList
import java.lang.Integer.min

class HomeGenreAdapter: RecyclerView.Adapter<HomeGenreAdapter.ViewHolder>() {

    private val genreData = ArrayList<Genre>()

    inner class ViewHolder(private val binding: ItemListGenreBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Genre) {
            binding.tvHomeGenreName.text = item.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = ItemListGenreBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return min(this.genreData.size, 10)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val holderData = this.genreData[position]
        holder.bind(holderData)
    }

    fun setData(dataList: List<Genre>) {
        this.genreData.apply {
            clear()
            addAll(dataList)
        }
        notifyDataSetChanged()
    }
}