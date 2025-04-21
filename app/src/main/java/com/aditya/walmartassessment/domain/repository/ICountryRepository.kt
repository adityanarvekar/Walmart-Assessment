package com.aditya.walmartassessment.domain.repository

import com.aditya.walmartassessment.data.utility.Result
import com.aditya.walmartassessment.domain.dto.CountryDomain
import kotlinx.coroutines.flow.Flow

interface ICountryRepository {
    suspend fun getCountryListFromApi(): Flow<Result<List<CountryDomain>>>
}