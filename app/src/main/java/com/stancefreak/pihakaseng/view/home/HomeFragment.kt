package com.stancefreak.pihakaseng.view.home

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.util.DisplayMetrics
import android.util.Log
import androidx.core.text.bold
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
import java.math.RoundingMode
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

    override fun setupToolbar() {
    }

    override fun setupViews() {
        val nextItemVisiblePx = resources.getDimension(R.dimen.viewpager_next_item_visible)
        val currentItemHorizontalMarginPx =
            resources.getDimension(R.dimen.viewpager_current_item_horizontal_margin)
        val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
        genreAdapter = HomeGenreAdapter()
        promoAdapter = HomePromoAdapter()
        nowPlayingAdapter = HomePlayingMovieAdapter(vm)
        binding.apply {
            vm.fetchHomeContent()
            tvHomeRegion.text = regionList.random()
            rvHomeGenreList.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                adapter = genreAdapter
            }
            vpHomeNowPlaying.apply {
                orientation = ViewPager2.ORIENTATION_HORIZONTAL
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
            }
        }
    }

    override fun setupObservers() {
        val startPos = Int.MAX_VALUE / 2
        vm.apply {
            observeMovieList().observe(viewLifecycleOwner) { data ->
                binding.apply {
                    vpHomePromoSlider.apply {
                        orientation = ViewPager2.ORIENTATION_HORIZONTAL
                        adapter = promoAdapter
                        setCurrentItem(startPos - (startPos % data.results.size), false)
                    }
                    fetchVpState().observe(viewLifecycleOwner) { savedPosition ->
                        vpHomeNowPlaying.apply {
                            adapter = nowPlayingAdapter
//                            setCurrentItem(startPos - (startPos % data.results.size), true)
                            if (savedPosition != null && savedPosition != 0) {
                                Log.d("tes saved item pos :", savedPosition.toString())
//                                setCurrentItem(savedPosition % data.results.size, false)
                                setCurrentItem(savedPosition, false)
                            }
                            else {
//                                setCurrentItem(startPos % data.results.size, true)
                                setCurrentItem(2, false)
                            }
                            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                                override fun onPageSelected(position: Int) {
                                    Log.d("tes selected item pos :", position.toString())
                                    super.onPageSelected(position)
//                                    val itemPosition = position % data.results.size
                                    val itemPosition = (position + data.results.size - 2) % data.results.size
                                    val roundedRating = data.results[itemPosition].voteAverage.toBigDecimal().setScale(1, RoundingMode.UP).toDouble()
                                    val marketText = SpannableStringBuilder()
                                        .append("Film ini dapat rating $roundedRating dari penonton lho!")
                                        .bold { append(" Harus ditonton nih!") }
                                    tvHomeMovieTitle.text =
                                        data.results[itemPosition].title
                                    tvHomeMovieMarket.text = marketText
                                }

                                override fun onPageScrolled(
                                    position: Int,
                                    positionOffset: Float,
                                    positionOffsetPixels: Int
                                ) {
                                    Log.d("tes scrolled item pos :", position.toString())
                                    super.onPageScrolled(position, positionOffset, positionOffsetPixels)
//                                    val itemPosition = position % data.results.size

                                    val itemPosition = (position + data.results.size - 2) % data.results.size
                                    val roundedRating = data.results[itemPosition].voteAverage.toBigDecimal().setScale(1, RoundingMode.UP).toDouble()
                                    val marketText = SpannableStringBuilder()
                                        .append("Film ini dapat rating $roundedRating dari penonton lho!")
                                        .bold { append(" Harus ditonton nih!") }
                                    tvHomeMovieTitle.text =
                                        data.results[itemPosition].title
                                    tvHomeMovieMarket.text = marketText
                                }

                                override fun onPageScrollStateChanged(state: Int) {
                                    super.onPageScrollStateChanged(state)

                                    // range data 0 - 21
                                    val fakeSize = data.results.size + 2
                                    val currentPosition = binding.vpHomeNowPlaying.currentItem
                                    if (state == ViewPager2.SCROLL_STATE_IDLE) {
                                        if (currentPosition == 0) {
                                            vpHomeNowPlaying.setCurrentItem(fakeSize - 2, false)
                                        }
                                        else if (currentPosition == fakeSize - 1) {
                                            vpHomeNowPlaying.setCurrentItem(1, false)
                                        }
                                    }
                                    else if (state == ViewPager2.SCROLL_STATE_DRAGGING && currentPosition == fakeSize) {
                                        vpHomeNowPlaying.setCurrentItem(2, false)
                                    }

                                }
                            })
                        }
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