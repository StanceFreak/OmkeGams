package com.stancefreak.pihakaseng.view.detail

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator
import com.stancefreak.pihakaseng.R
import com.stancefreak.pihakaseng.base.BaseFragment
import com.stancefreak.pihakaseng.databinding.FragmentDetailBinding
import com.stancefreak.pihakaseng.model.remote.request.DetailTabData
import com.stancefreak.pihakaseng.view.adapter.DetailTabAdapter
import com.stancefreak.pihakaseng.view.jadwal.JadwalFragment
import com.stancefreak.pihakaseng.view.sinopsis.SinopsisFragment
import dagger.hilt.android.AndroidEntryPoint
import java.math.RoundingMode

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding, DetailViewModel>(FragmentDetailBinding::inflate) {

    override val vm: DetailViewModel by viewModels()

    override fun setupToolbar() {
        binding.apply {
//            toolbarDetailHeader.setNavigationIcon(R.drawable.ic_arrow_back)
            (requireActivity() as AppCompatActivity).apply {
                setSupportActionBar(toolbarDetailHeader)
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                toolbarDetailHeader.title = ""
                toolbarDetailHeader.setNavigationOnClickListener {
                    onBackPressedDispatcher.onBackPressed()
                }
            }
        }
    }

    override fun setupViews() {
        val movieId = arguments?.getInt("movieId")
        if (movieId != null) {
            vm.apply {
                fetchMovieDetail(movieId)
                fetchMovieTrailer(movieId)
            }
        }
    }

    override fun setupObservers() {
        binding.apply {
            vm.apply {
                observerMovieDetail().observe(viewLifecycleOwner) { data ->
                    if (data != null) {
                        appbarDetailHeaderContainer.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
                            val scrollRange = appbarDetailHeaderContainer.totalScrollRange

                            if (scrollRange + verticalOffset == 0) {
                                // collapsed state
                                tvDetailMovieHeaderTitle.isGone = false
                                toolbarDetailHeader.setNavigationIcon(R.drawable.ic_arrow_back)
//                                ivTest.isGone = false
//                                Glide.with(requireContext())
//                                    .asDrawable()
//                                    .load("https://image.tmdb.org/t/p/w500${data.backdropPath}")
//                                    .centerCrop()
//                                    .into(object : CustomTarget<Drawable>() {
//                                        override fun onResourceReady(
//                                            resource: Drawable,
//                                            transition: Transition<in Drawable>?
//                                        ) {
//                                            ivTest.setImageDrawable(resource)
//                                        }
//
//                                        override fun onLoadCleared(placeholder: Drawable?) {
//                                        }
//
//                                    })
                            }
                            else {
                                // expanded state
                                tvDetailMovieHeaderTitle.isGone = true
                                toolbarDetailHeader.setNavigationIcon(R.drawable.ic_arrow_back_circular)
//                                ivTest.isGone = true
                            }
                        }
                        val roundedRating = data.voteAverage.toBigDecimal().setScale(1, RoundingMode.UP).toFloat()
                        val hours = data.runtime / 60
                        val minutes = data.runtime % 60
                        val movieDuration = "$hours jam $minutes menit";
                        val isoIndex = data.releaseDates.results.indexOfFirst { item ->
                            item.iso31661 == "US" || item.iso31661 == data.originCountry.firstOrNull()
                        }
                        val certIndex = data.releaseDates.results[isoIndex].releaseDates.indexOfFirst {item ->
                            item.certification.isNotEmpty()
                        }
                        val directorIndex = data.credits.crew.indexOfFirst { item ->
                            item.job == "Director"
                        }
                        val tabAdapter = DetailTabAdapter(
                            data,
                            (activity as AppCompatActivity).supportFragmentManager,
                            2,
                            lifecycle
                        )
                        val tabList = listOf("Sinopsis", "Jadwal")
                        tvDetailMovieHeaderTitle.text = data.title
                        Glide.with(ivDetailMovieBackdrop.context)
                            .load("https://image.tmdb.org/t/p/w500${data.backdropPath}")
                            .into(ivDetailMovieBackdrop)
                        Glide.with(ivDetailMoviePoster.context)
                            .load("https://image.tmdb.org/t/p/w500${data.posterPath}")
                            .into(ivDetailMoviePoster)
                        tvDetailMovieTitle.text = data.title
                        tvDetailMovieGenre.text = "Genre\t\t\t\t\t\t\t${data.genres.firstOrNull()?.name}"
                        tvDetailMovieDuration.text = "Durasi\t\t\t\t\t\t\t$movieDuration"
                        tvDetailMovieDirector.text = "Sutradara\t\t\t\t${data.credits.crew[directorIndex].name}"
                        tvDetailMovieAgeRating.text = "Rating Usia\t\t\t${data.releaseDates.results[isoIndex].releaseDates[certIndex].certification}"
                        rbDetailMovieRating.rating = roundedRating
                        tvDetailMovieRatingValue.text = roundedRating.toString()
                        tvDetailMovieRatingCount.text = "${data.voteCount} Vote"

                        observeMovieTrailer().observe(viewLifecycleOwner) { trailer ->
                            val tabList = listOf("Sinopsis", "Jadwal")
                            if (trailer != null) {
                                val sinopsis = DetailTabData(
                                    data,
                                    trailer.results.sortedByDescending {
                                        it.type == "Trailer"
                                    }
                                )
                                val tabAdapter = DetailTabAdapter(
                                    sinopsis,
                                    (activity as AppCompatActivity).supportFragmentManager,
                                    2,
                                    lifecycle
                                )
                                vpDetailTabContainer.apply {
                                    adapter = tabAdapter
                                    isUserInputEnabled = false
                                }
                                TabLayoutMediator(tlDetailTabContainer, vpDetailTabContainer) { tab, pos ->
                                    tab.text = tabList[pos]
                                }.attach()
                            }
                        }
                    }
                }
            }
        }
    }

}