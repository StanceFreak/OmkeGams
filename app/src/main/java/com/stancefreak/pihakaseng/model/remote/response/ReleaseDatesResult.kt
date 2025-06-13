package com.stancefreak.pihakaseng.model.remote.response


import com.google.gson.annotations.SerializedName
import com.stancefreak.pihakaseng.model.remote.response.ReleaseDate

data class ReleaseDatesResult(
    @SerializedName("iso_3166_1")
    val iso31661: String = "",
    @SerializedName("release_dates")
    val releaseDates: List<ReleaseDate> = listOf()
)