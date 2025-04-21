package com.aditya.walmartassessment.presentation.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aditya.walmartassessment.data.utility.Result
import com.aditya.walmartassessment.domain.dto.CountryDomain
import com.aditya.walmartassessment.domain.usecase.GetCountryListUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * viewmodel to track UI and configuration changes
 */
class CountryViewModel(
    private val useCase: GetCountryListUseCase
) : ViewModel() {
    private val _countryListState = MutableStateFlow<Result<List<CountryDomain>>>(
        Result.Empty
    )
    val countryListState: StateFlow<Result<List<CountryDomain>>> =
        _countryListState.asStateFlow()

    var scrollPosition: Int = 0

    // updating if negative case
    private val ceh = CoroutineExceptionHandler { _, e ->
        e.printStackTrace()
        _countryListState.value = Result.Error(e as Exception)
    }

    /**
     * fires the use case to get country list data
     * collects data and update UI state
     */
    fun getCountryList() {
        viewModelScope.launch(ceh) {
            useCase().collect { result ->
                _countryListState.value = result
            }
        }
    }
}