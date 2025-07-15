package com.stancefreak.pihakaseng.view.adapter

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.stancefreak.pihakaseng.R
import com.stancefreak.pihakaseng.databinding.ItemListJadwalDateBinding
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date

class JadwalDateAdapter: RecyclerView.Adapter<JadwalDateAdapter.ViewHolder>() {

    private val dates = ArrayList<LocalDate>()
    private val currWeekList = ArrayList<LocalDate>()

    inner class ViewHolder(private val binding: ItemListJadwalDateBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: LocalDate) {
            binding.apply {
                val indoMonths = listOf(
                    "Januari",
                    "Februari",
                    "Maret",
                    "April",
                    "Mei",
                    "Juni",
                    "Juli",
                    "Agustus",
                    "September",
                    "Oktober",
                    "November",
                    "Desember"
                )
                val indoDayName = listOf(
                    "Senin",
                    "Selasa",
                    "Rabu",
                    "Kamis",
                    "Jumat",
                    "Sabtu",
                    "Minggu",
                )

                val dayIndex = item.dayOfWeek.value - 1
                val currDate = Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                val monthName = DateTimeFormatter.ofPattern("M")
                val dateNumber = DateTimeFormatter.ofPattern("dd")
                val monthIndex = monthName.format(item).toInt() - 1

                val monthLabel = indoMonths[monthIndex].substring(0, 3)
                val dayLabel = indoDayName[dayIndex].substring(0, 3)
                tvItemDateLabel.text = "${dateNumber.format(item)} $monthLabel"
                tvItemDateDay.text = dayLabel

                if (!currWeekList.contains(item) && item != currDate) {
                    cvItemDateContainer.apply {
                        strokeColor = ContextCompat.getColor(itemView.context, R.color.light_gray)
                        setCardBackgroundColor(ContextCompat.getColor(itemView.context, R.color.light_gray))
                    }
                    tvItemDateDay.setTextColor(ContextCompat.getColor(itemView.context, R.color.gray_3))
                    tvItemDateLabel.setTextColor(ContextCompat.getColor(itemView.context, R.color.gray_3))
                }
                else {
                    if (item == currDate) {
                        cvItemDateContainer.apply {
                            strokeColor = ContextCompat.getColor(itemView.context, R.color.white)
                            setCardBackgroundColor(ContextCompat.getColor(itemView.context, R.color.indigo))
                        }
                        tvItemDateDay.apply {
                            text = "Hari Ini"
                            setTextColor(ContextCompat.getColor(itemView.context, R.color.white))
                        }
                        tvItemDateLabel.setTextColor(ContextCompat.getColor(itemView.context, R.color.white))
                    }
                    else {
                        cvItemDateContainer.apply {
                            strokeColor = ContextCompat.getColor(itemView.context, R.color.indigo)
                            setCardBackgroundColor(ContextCompat.getColor(itemView.context, R.color.white))
                        }
                        tvItemDateDay.apply {
                            setTextColor(ContextCompat.getColor(itemView.context, R.color.indigo))
                        }
                        tvItemDateLabel.setTextColor(ContextCompat.getColor(itemView.context, R.color.indigo))
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = ItemListJadwalDateBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return dates.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dates[position]
        holder.bind(data)
    }

    fun setData(dataList: List<LocalDate>, weekList: List<LocalDate>) {
        dates.apply {
            clear()
            addAll(dataList)
        }
        currWeekList.apply {
            clear()
            addAll(weekList)
        }
        notifyDataSetChanged()
    }

}