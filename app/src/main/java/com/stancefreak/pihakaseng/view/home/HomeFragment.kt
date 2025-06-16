package com.stancefreak.pihakaseng.view.home

import android.util.DisplayMetrics
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.MarginPageTransformer
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
        val currentItemHorizontalMarginPx =
            resources.getDimension(R.dimen.viewpager_current_item_horizontal_margin)
        val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
        val startPos = Int.MAX_VALUE / 2
        vm.apply {
            observeMovieList().observe(viewLifecycleOwner) { data ->
                binding.apply {
                    vpHomePromoSlider.apply {
                        orientation = ViewPager2.ORIENTATION_HORIZONTAL
                        adapter = promoAdapter
                        setCurrentItem(startPos - (startPos % data.results.size), false)
                    }
                    vpHomeNowPlaying.apply {
                        orientation = ViewPager2.ORIENTATION_HORIZONTAL
                        adapter = nowPlayingAdapter
                        setCurrentItem(startPos - (startPos % data.results.size), false)
                        offscreenPageLimit = 1
                        val offsetPx = 50.dpToPx(resources.displayMetrics)
                        val pageMarginPx = 10.dpToPx(resources.displayMetrics)
                        // control the padding on each items
                        setPadding(offsetPx, 0, offsetPx, 0)
                        setPageTransformer(MarginPageTransformer(pageMarginPx))
                        val pageTransformer = ViewPager2.PageTransformer { page, position ->
                            page.apply {
                                // control the distance between the items
                                translationX = -pageTranslationX * position
                                // control the size of the offscreen size items
                                scaleY = 1 - (0.1f * abs(position))
                                // control the blur on the last and next items
                                alpha = 0.5f + (1 - abs(position))
                            }
                        }
                        setPageTransformer(pageTransformer)
                        val itemDecoration = HorizontalMarginItemDecoration(
                            requireContext(),
                            R.dimen.viewpager_current_item_horizontal_margin
                        )
                        addItemDecoration(itemDecoration)
                        registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                            override fun onPageSelected(position: Int) {
                                super.onPageSelected(position)
                                tvHomeMovieTitle.text = data.results[position % data.results.size].title
                            }

                            override fun onPageScrolled(
                                position: Int,
                                positionOffset: Float,
                                positionOffsetPixels: Int
                            ) {
                                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                                tvHomeMovieTitle.text = data.results[position % data.results.size].title
                            }
                        })
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

    fun Int.dpToPx(displayMetrics: DisplayMetrics): Int = (this * displayMetrics.density).toInt()
    fun Int.pxToDp(displayMetrics: DisplayMetrics): Int = (this / displayMetrics.density).toInt()


}