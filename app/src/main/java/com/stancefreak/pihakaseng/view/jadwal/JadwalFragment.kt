package com.stancefreak.pihakaseng.view.jadwal

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.stancefreak.pihakaseng.R
import com.stancefreak.pihakaseng.base.BaseFragment
import com.stancefreak.pihakaseng.databinding.FragmentJadwalBinding
import com.stancefreak.pihakaseng.view.adapter.JadwalDateAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.TemporalAdjusters.next
import java.util.Calendar
import java.util.Date

@AndroidEntryPoint
class JadwalFragment :
    BaseFragment<FragmentJadwalBinding, JadwalViewModel>(FragmentJadwalBinding::inflate) {

    override val vm: JadwalViewModel by viewModels()
    private lateinit var jadwalAdapter: JadwalDateAdapter

    override fun setupToolbar() {
    }

    override fun setupViews() {
        binding.apply {
            jadwalAdapter = JadwalDateAdapter()
            val mCalendar = Calendar.getInstance()
            mCalendar.add(Calendar.MONTH, 1)
            val nextMonthDate =
                mCalendar.time.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
            val currentDate = Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
            val nextSunday = LocalDate.now().with(next(DayOfWeek.SUNDAY))

            val dateList = generateDateRange(currentDate, nextMonthDate)
            val currWeekDateList = generateDateRange(currentDate, nextSunday)

            rvJadwalCalendar.apply {
                (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                adapter = jadwalAdapter
            }
            jadwalAdapter.setData(dateList, currWeekDateList)
        }
    }

    override fun setupObservers() {
    }

    private fun generateDateRange(startDate: LocalDate, endDate: LocalDate): List<LocalDate> {
        val dates = mutableListOf<LocalDate>()
        var current = startDate
        while (current <= endDate) {
            dates.add(current)
            current = current.plusDays(1)
        }
        return dates
    }

}