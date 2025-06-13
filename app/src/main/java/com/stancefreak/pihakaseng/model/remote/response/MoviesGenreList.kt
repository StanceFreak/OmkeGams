package com.stancefreak.pihakaseng.model.remote.response


import com.google.gson.annotations.SerializedName

data class MoviesGenreList(
    @SerializedName("genres")
    val genres: List<Genre> = listOf()
)