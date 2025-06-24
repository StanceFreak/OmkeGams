package com.stancefreak.pihakaseng.model.remote.request

import com.stancefreak.pihakaseng.model.remote.response.MovieDetail
import com.stancefreak.pihakaseng.model.remote.response.MovieTrailerResult

data class DetailTabData(
    val movieDetail: MovieDetail,
    val movieTrailer: List<MovieTrailerResult>
)
