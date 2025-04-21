package com.aditya.walmartassessment.data.repository

import com.aditya.walmartassessment.data.mapper.CountryMapper
import com.aditya.walmartassessment.data.services.ApiService
import com.aditya.walmartassessment.data.services.result
import com.aditya.walmartassessment.domain.repository.ICountryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn

/**
 * CountryRepository class to get country data from Api.
 * Fetch the country data and use mapper to map the list to our data model.
 */
class RepositoryImpl(
    private val apiService: ApiService
) : ICountryRepository {
    override suspend fun getCountryListFromApi() = result(
        call = { apiService.getCountryList() },
        mapper = { CountryMapper.mapToDomainList(it) }
    ).flowOn(Dispatchers.IO)
}