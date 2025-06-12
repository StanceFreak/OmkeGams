package com.stancefreak.combaja.data.response


import com.google.gson.annotations.SerializedName

data class ReleaseDates(
    @SerializedName("results")
    val results: List<ReleaseDatesResult> = listOf()
)