package com.stancefreak.pihakaseng.view.sinopsis

import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.stancefreak.pihakaseng.base.BaseFragment
import com.stancefreak.pihakaseng.databinding.FragmentSinopsisBinding
import com.stancefreak.pihakaseng.model.remote.request.DetailTabData
import com.stancefreak.pihakaseng.model.remote.request.SinopsisParentData
import com.stancefreak.pihakaseng.model.remote.response.Cast
import com.stancefreak.pihakaseng.view.adapter.SinopsisParentAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SinopsisFragment(
    private val data: DetailTabData
) : BaseFragment<FragmentSinopsisBinding, SinopsisViewModel>(FragmentSinopsisBinding::inflate) {

    private lateinit var parentAdapter: SinopsisParentAdapter
    private val crewList = ArrayList<Cast>()
    override val vm: ViewModel by viewModels()

    override fun setupToolbar() {
    }

    override fun setupViews() {
        binding.apply {
            val headerList = listOf("Pemeran", "Video")
            val directorIndex = data.movieDetail.credits.crew.indexOfFirst { item ->
                item.job == "Director"
            }
            crewList.apply {
                clear()
                add(data.movieDetail.credits.crew[directorIndex])
                addAll(data.movieDetail.credits.cast)
            }
            val parentData = SinopsisParentData(
                headerList,
                data.movieTrailer,
                crewList
            )
            parentAdapter = SinopsisParentAdapter(parentData)
            tvSinopsisMovieDesc.text = data.movieDetail.overview
            rvSinopsisParent.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = parentAdapter
            }
            parentAdapter.notifyDataSetChanged()
        }
    }

    override fun setupObservers() {
    }

}