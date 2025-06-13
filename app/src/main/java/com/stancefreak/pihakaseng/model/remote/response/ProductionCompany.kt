package com.stancefreak.pihakaseng.model.remote.response


import com.google.gson.annotations.SerializedName

data class ProductionCompany(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("logo_path")
    val logoPath: Any? = null,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("origin_country")
    val originCountry: String = ""
)