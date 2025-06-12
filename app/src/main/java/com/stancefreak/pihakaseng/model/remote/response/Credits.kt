package com.stancefreak.combaja.data.response


import com.google.gson.annotations.SerializedName

data class Credits(
    @SerializedName("cast")
    val cast: List<Cast> = listOf(),
    @SerializedName("crew")
    val crew: List<Crew> = listOf()
)