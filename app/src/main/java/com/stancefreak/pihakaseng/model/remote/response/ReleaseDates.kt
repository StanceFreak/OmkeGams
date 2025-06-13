package com.stancefreak.pihakaseng.model.remote.response


import com.google.gson.annotations.SerializedName

data class ReleaseDates(
    @SerializedName("results")
    val results: List<ReleaseDatesResult> = listOf()
)