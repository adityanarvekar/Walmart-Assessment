package com.aditya.walmartassessment.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.aditya.walmartassessment.data.utility.Result
import com.aditya.walmartassessment.domain.dto.CountryDomain
import com.aditya.walmartassessment.domain.usecase.GetCountryListUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CountryViewModelTest {
    private lateinit var viewModel: CountryViewModel
    private lateinit var useCase: GetCountryListUseCase
    private lateinit var testDispatcher: TestDispatcher

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        useCase = mockk()
        testDispatcher = StandardTestDispatcher()
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `getCountryList update countryListState successfully`() = runTest(testDispatcher) {
        val fakeDomainList = listOf(
            CountryDomain(
                name = "United States of America",
                region = "NA", code = "US", capital = "Washington,D.C."
            ),
            CountryDomain(
                name = "Uruguay",
                region = "SA", code = "UY", capital = "Montevideo"
            )
        )
        val fakeSuccessFlow = flow {
            emit(Result.Loading)
            delay(100L)
            emit(Result.Success(fakeDomainList))
        }
        coEvery { useCase() } returns fakeSuccessFlow

        viewModel = CountryViewModel(useCase)
        viewModel.getCountryList()
        val stateList = mutableListOf<Result<List<CountryDomain>>>()
        val job = launch {
            viewModel.countryListState.collect {
                stateList.add(it)
            }
        }
        advanceUntilIdle()
        job.cancel()

        assert(stateList[0] is Result.Loading)
        assert(stateList[1] is Result.Success)
        val successResult = stateList[1] as Result.Success
        assert(successResult.data == fakeDomainList)
    }

    @Test
    fun `getCountryList failed due to api issue`() = runTest(testDispatcher) {
        val fakeException = Exception("Network Error")
        val fakeErrorFlow = flow {
            emit(Result.Loading)
            delay(100L)
            emit(Result.Error(fakeException))
        }
        coEvery { useCase() } returns fakeErrorFlow

        viewModel = CountryViewModel(useCase)
        viewModel.getCountryList()
        val stateList = mutableListOf<Result<List<CountryDomain>>>()
        val job = launch {
            viewModel.countryListState.collect {
                stateList.add(it)
            }
        }
        advanceUntilIdle()
        job.cancel()

        assert(stateList[0] is Result.Loading)
        assert(stateList[1] is Result.Error)
        val errorResult = stateList[1] as Result.Error
        assert(errorResult.exception == fakeException)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}