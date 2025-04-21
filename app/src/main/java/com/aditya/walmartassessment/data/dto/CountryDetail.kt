package com.aditya.walmartassessment.data.dto

import com.google.gson.annotations.SerializedName

/**
 * data model defined according to requirements
 */
data class CountryDetail(
    @SerializedName("name")
    val name: String,

    @SerializedName("region")
    val region: String,

    @SerializedName("code")
    val code: String,

    @SerializedName("capital")
    val capital: String,
)