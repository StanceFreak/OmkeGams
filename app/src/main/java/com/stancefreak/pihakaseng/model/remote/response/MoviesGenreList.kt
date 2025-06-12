package com.stancefreak.combaja.data.response


import com.google.gson.annotations.SerializedName

data class MoviesGenreList(
    @SerializedName("genres")
    val genres: List<Genre> = listOf()
)