package com.aditya.walmartassessment.domain.usecase

import com.aditya.walmartassessment.domain.repository.ICountryRepository

class GetCountryListUseCase(
    private val countryRepo: ICountryRepository
) {
    suspend operator fun invoke() = countryRepo.getCountryListFromApi()
}