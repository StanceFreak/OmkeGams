package com.stancefreak.pihakaseng.model.remote.response


import com.google.gson.annotations.SerializedName

data class MoviesList(
    @SerializedName("page")
    val page: Int = 0,
    @SerializedName("results")
    val results: List<Result> = listOf(),
    @SerializedName("total_pages")
    val totalPages: Int = 0,
    @SerializedName("total_results")
    val totalResults: Int = 0
)