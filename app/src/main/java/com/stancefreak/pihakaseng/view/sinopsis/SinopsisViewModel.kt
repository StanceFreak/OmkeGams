package com.stancefreak.pihakaseng.view.sinopsis

import androidx.lifecycle.ViewModel
import com.stancefreak.pihakaseng.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SinopsisViewModel @Inject constructor(
    private val repo: AppRepository
): ViewModel() {
}