package com.aditya.walmartassessment.domain.dto

/**
 * @param capital - capital name
 * @param code - country code
 * @param name - country name
 * @param region - country region
 */
data class CountryDomain(
    val capital: String,
    val code: String,
    val name: String,
    val region: String
)