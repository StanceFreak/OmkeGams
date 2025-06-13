package com.stancefreak.pihakaseng.model.remote.response


import com.google.gson.annotations.SerializedName
import com.stancefreak.pihakaseng.model.remote.response.Cast
import com.stancefreak.pihakaseng.model.remote.response.Crew

data class Credits(
    @SerializedName("cast")
    val cast: List<Cast> = listOf(),
    @SerializedName("crew")
    val crew: List<Crew> = listOf()
)