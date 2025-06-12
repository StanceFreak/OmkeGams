package com.stancefreak.pihakaseng.view.home

import androidx.lifecycle.ViewModel
import com.stancefreak.pihakaseng.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repo: AppRepository
):ViewModel() {
}