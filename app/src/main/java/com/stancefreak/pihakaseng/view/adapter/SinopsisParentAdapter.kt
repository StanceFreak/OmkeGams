package com.stancefreak.pihakaseng.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stancefreak.pihakaseng.databinding.ItemListSinopsisParentBinding
import com.stancefreak.pihakaseng.model.remote.request.SinopsisParentData

class SinopsisParentAdapter(
    private val data: SinopsisParentData
): RecyclerView.Adapter<SinopsisParentAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemListSinopsisParentBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(header: String, data: SinopsisParentData) {
            binding.apply {
                val childAdapter = SinopsisChildAdapter(header, data)
                tvItemSinopsisParentHeader.text = header
                if (header == "Pemeran") {
                    tvItemSinopsisParentSeeAll.visibility = View.GONE
                }
                else {
                    if (data.trailer.size > 10) {
                        tvItemSinopsisParentSeeAll.visibility = View.VISIBLE
                    }
                    else {
                        tvItemSinopsisParentSeeAll.visibility = View.GONE
                    }
                }
                rvSinopsisChildren.apply {
                    layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    adapter = childAdapter
                }
                childAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = ItemListSinopsisParentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return this.data.header.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val header = this.data.header[position]
        holder.bind(header, data)
    }
}