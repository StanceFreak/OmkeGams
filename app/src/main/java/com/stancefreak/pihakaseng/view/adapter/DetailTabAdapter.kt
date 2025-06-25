package com.stancefreak.pihakaseng.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.stancefreak.pihakaseng.model.remote.request.DetailTabData
import com.stancefreak.pihakaseng.view.jadwal.JadwalFragment
import com.stancefreak.pihakaseng.view.sinopsis.SinopsisFragment

class DetailTabAdapter(
    private val data: DetailTabData,
    fm: FragmentManager,
    private var totalTab: Int,
    lifecycle: Lifecycle
): FragmentStateAdapter(fm, lifecycle) {
    override fun getItemCount(): Int {
        return totalTab
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> SinopsisFragment(data)
            1 -> JadwalFragment()
            else -> JadwalFragment()
        }
    }
}