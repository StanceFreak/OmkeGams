package com.stancefreak.combaja.data.response


import com.google.gson.annotations.SerializedName

data class ReleaseDatesResult(
    @SerializedName("iso_3166_1")
    val iso31661: String = "",
    @SerializedName("release_dates")
    val releaseDates: List<ReleaseDate> = listOf()
)