package com.aditya.walmartassessment.data.mapper

import com.aditya.walmartassessment.data.dto.CountryDetail
import com.aditya.walmartassessment.domain.dto.CountryDomain


/**
 * utility to map the response to data model country detail
 */
object CountryMapper {
    private fun mapToDomain(countryDetail: CountryDetail): CountryDomain {
        return CountryDomain(
            capital = countryDetail.capital,
            code = countryDetail.code,
            name = countryDetail.name,
            region = countryDetail.region
        )
    }

    fun mapToDomainList(countryDetails: List<CountryDetail>): List<CountryDomain> {
        return countryDetails.map { mapToDomain(it) }
    }
}