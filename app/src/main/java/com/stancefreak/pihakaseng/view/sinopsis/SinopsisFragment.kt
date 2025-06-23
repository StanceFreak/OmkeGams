package com.stancefreak.pihakaseng.view.sinopsis

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.stancefreak.pihakaseng.R
import com.stancefreak.pihakaseng.base.BaseFragment
import com.stancefreak.pihakaseng.databinding.FragmentSinopsisBinding
import com.stancefreak.pihakaseng.model.remote.response.MovieDetail
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SinopsisFragment(
    private val data: MovieDetail
) : BaseFragment<FragmentSinopsisBinding, SinopsisViewModel>(FragmentSinopsisBinding::inflate) {

    override val vm: ViewModel by viewModels()

    override fun setupToolbar() {
    }

    override fun setupViews() {
        binding.apply {
            tvSinopsisMovieDesc.text = data.overview
        }
    }

    override fun setupObservers() {
    }

}