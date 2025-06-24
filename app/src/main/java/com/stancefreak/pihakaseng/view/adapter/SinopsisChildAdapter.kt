package com.stancefreak.pihakaseng.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.stancefreak.pihakaseng.databinding.ItemListSinopsisParentBinding
import com.stancefreak.pihakaseng.databinding.ItemListSinopsisPemeranBinding
import com.stancefreak.pihakaseng.databinding.ItemListSinopsisVideoBinding
import com.stancefreak.pihakaseng.model.remote.request.SinopsisParentData
import com.stancefreak.pihakaseng.model.remote.response.Cast
import com.stancefreak.pihakaseng.model.remote.response.Crew
import com.stancefreak.pihakaseng.model.remote.response.MovieTrailerResult
import java.lang.Integer.min

class SinopsisChildAdapter(
    private val header: String,
    private val childData: SinopsisParentData
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val TYPE_PEMERAN = 0
        const val TYPE_VIDEO = 1
    }

    inner class VideoViewHolder(private val binding: ItemListSinopsisVideoBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind (item: MovieTrailerResult) {
            binding.apply {
                Glide.with(itemView.context)
                    .load("https://img.youtube.com/vi/${item.key}/sddefault.jpg")
                    .into(ivItemSinopsisVideoThumb)
            }
        }
    }

    inner class PemeranViewHolder(private val binding: ItemListSinopsisPemeranBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind (item: Cast) {
            binding.apply {
                Glide.with(itemView.context)
                    .load("https://image.tmdb.org/t/p/w500${item.profilePath}")
                    .into(ivItemSinopsisPemeranThumb)
                tvItemSinopsisPemeranName.text = item.name
                if (item.character.isNotEmpty()) {
                    tvItemSinopsisPemeranChar.text = item.character
                }
                else {
                    tvItemSinopsisPemeranChar.text = "Sutradara"
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == TYPE_PEMERAN)  {
            val itemBinding = ItemListSinopsisPemeranBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return PemeranViewHolder(itemBinding)
        }
        else {
            val itemBinding = ItemListSinopsisVideoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return VideoViewHolder(itemBinding)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (header) {
            "Pemeran" -> TYPE_PEMERAN
            "Video" -> TYPE_VIDEO
            else -> throw IllegalArgumentException("Unknown type")
        }
    }

    override fun getItemCount(): Int {
        return if (header == "Pemeran") {
            this.childData.crew.size
        } else {
            if (this.childData.trailer.size > 10) {
                min(this.childData.trailer.size, 10)
            }
            else {
                this.childData.trailer.size
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.apply {
            when (header) {
                "Pemeran" -> {
                    val data = childData.crew[position]
                    (holder as PemeranViewHolder).bind(data)
                }
                "Video" -> {
                    val data = childData.trailer[position]
                    (holder as VideoViewHolder).bind(data)
                }
            }
        }
    }
}