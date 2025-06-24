package com.stancefreak.pihakaseng.model.remote.response


import com.google.gson.annotations.SerializedName

data class MovieTrailer(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("results")
    val results: List<MovieTrailerResult> = listOf()
)