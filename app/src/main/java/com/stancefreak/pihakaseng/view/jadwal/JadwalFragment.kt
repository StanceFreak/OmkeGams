package com.stancefreak.pihakaseng.view.jadwal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.stancefreak.pihakaseng.R
import com.stancefreak.pihakaseng.base.BaseFragment
import com.stancefreak.pihakaseng.databinding.FragmentJadwalBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class JadwalFragment : BaseFragment<FragmentJadwalBinding, JadwalViewModel>(FragmentJadwalBinding::inflate) {

    override val vm: ViewModel by viewModels()

    override fun setupToolbar() {
    }

    override fun setupViews() {
    }

    override fun setupObservers() {
    }

}