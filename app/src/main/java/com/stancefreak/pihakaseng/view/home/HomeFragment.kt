package com.stancefreak.pihakaseng.view.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.stancefreak.pihakaseng.R
import com.stancefreak.pihakaseng.base.BaseFragment
import com.stancefreak.pihakaseng.databinding.FragmentHomeBinding
import com.stancefreak.pihakaseng.util.HorizontalMarginItemDecoration
import com.stancefreak.pihakaseng.view.adapter.HomeGenreAdapter
import com.stancefreak.pihakaseng.view.adapter.HomePlayingMovieAdapter
import com.stancefreak.pihakaseng.view.adapter.HomePromoAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.abs

@AndroidEntryPoint
class HomeFragment :
    BaseFragment<FragmentHomeBinding, HomeViewModel>(FragmentHomeBinding::inflate) {

    private lateinit var genreAdapter: HomeGenreAdapter
    private lateinit var promoAdapter: HomePromoAdapter
    private lateinit var nowPlayingAdapter: HomePlayingMovieAdapter
    override val vm: HomeViewModel by viewModels()
    private val regionList = arrayListOf<String>(
        "Aceh",
        "Bali",
        "Bangka Belitung",
        "Banten",
        "Bengkulu",
        "Daerah Istimewa Yogyakarta",
        "DKI Jakarta",
        "Gorontalo",
        "Jambi",
        "Jawa Barat",
        "Jawa Tengah",
        "Jawa Timur",
        "Kalimantan Barat",
        "Kalimantan Selatan",
        "Kalimantan Tengah ",
        "Kalimantan Timur",
        "Kalimantan Utara",
        "Kepulauan Bangka Belitung",
        "Kepulauan Riau",
        "Lampung",
        "Maluku",
        "Maluku Utara",
        "Nusa Tenggara Barat",
        "Nusa Tenggara Timur",
        "Papua",
        "Papua Barat",
        "Papua Barat Daya",
        "Papua Pegunungan",
        "Papua Selatan",
        "Papua Tengah",
        "Riau",
        "Sulawesi Barat",
        "Sulawesi Selatan",
        "Sulawesi Tengah",
        "Sulawesi Tenggara",
        "Sulawesi Utara",
        "Sumatera Barat",
        "Sumatera Selatan",
        "Sumatera Utara",
    )

    override fun setupViews() {
        genreAdapter = HomeGenreAdapter()
        promoAdapter = HomePromoAdapter()
        nowPlayingAdapter = HomePlayingMovieAdapter()
        binding.apply {
            vm.fetchHomeContent()
            tvHomeRegion.text = regionList.random()
            rvHomeGenreList.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                adapter = genreAdapter
            }
        }
    }

    override fun setupObservers() {
        val nextItemVisiblePx = resources.getDimension(R.dimen.viewpager_next_item_visible)
        val currentItemHorizontalMarginPx = resources.getDimension(R.dimen.viewpager_current_item_horizontal_margin)
        val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
        vm.apply {
            observeMovieList().observe(viewLifecycleOwner) { data ->
                binding.apply {
                    vpHomePromoSlider.apply {
                        orientation = ViewPager2.ORIENTATION_HORIZONTAL
                        adapter = promoAdapter
                        currentItem = Int.MAX_VALUE / 2
                    }
                    vpHomeNowPlaying.apply {
                        orientation = ViewPager2.ORIENTATION_HORIZONTAL
                        adapter = nowPlayingAdapter
                        currentItem = Int.MAX_VALUE / 2
                        offscreenPageLimit = 1
                        setPageTransformer { page, position ->
                            page.apply {
                                translationX = -pageTranslationX * position
                                scaleY = 1 - (0.25f * abs(position))
                                alpha = 0.25f + (1 - abs(position))
                            }
                        }
                        val itemDecoration = HorizontalMarginItemDecoration(
                            requireContext(),
                            R.dimen.viewpager_current_item_horizontal_margin
                        )
                        addItemDecoration(itemDecoration)
                    }
                }
                promoAdapter.setData(data.results)
                nowPlayingAdapter.setData(data.results)

            }
            observeGenreList().observe(viewLifecycleOwner) { data ->
                genreAdapter.setData(data.genres)
            }
        }
    }

}