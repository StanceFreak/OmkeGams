package com.stancefreak.pihakaseng.view.jadwal

import androidx.lifecycle.ViewModel
import com.stancefreak.pihakaseng.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class JadwalViewModel @Inject constructor(
    private val repo: AppRepository
): ViewModel() {
}