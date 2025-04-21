package com.aditya.walmartassessment.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aditya.walmartassessment.domain.usecase.GetCountryListUseCase

/**
 * track instance of viewmodel
 */
class CountryViewModelFactory(
    private val useCase: GetCountryListUseCase
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CountryViewModel::class.java)) {
            return CountryViewModel(useCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class!")
    }
}