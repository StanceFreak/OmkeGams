package com.stancefreak.pihakaseng.model.remote.request

import com.stancefreak.pihakaseng.model.remote.response.Cast
import com.stancefreak.pihakaseng.model.remote.response.MovieTrailer
import com.stancefreak.pihakaseng.model.remote.response.MovieTrailerResult

data class SinopsisParentData(
    val header: List<String>,
    val trailer: List<MovieTrailerResult>,
    val crew: List<Cast>
)
