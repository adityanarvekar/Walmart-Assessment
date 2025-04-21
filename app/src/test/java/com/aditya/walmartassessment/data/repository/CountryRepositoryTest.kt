package com.aditya.walmartassessment.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.aditya.walmartassessment.data.dto.CountryDetail
import com.aditya.walmartassessment.data.services.ApiService
import com.aditya.walmartassessment.data.utility.Result
import com.aditya.walmartassessment.domain.dto.CountryDomain
import com.aditya.walmartassessment.domain.repository.ICountryRepository
import retrofit2.Response
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class CountryRepositoryTest {
    lateinit var apiService: ApiService
    lateinit var repo: ICountryRepository
    lateinit var testDispatcher: TestDispatcher

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        apiService = mockk()
        testDispatcher = StandardTestDispatcher()
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `getCountryListFromApi returns success`() = runTest(testDispatcher) {
        val fakeResponse = listOf(
            CountryDetail(
                capital = "Bahamas, NA", code = "BS",
                name = "Nassau", region = "Bahamas"
            )
        )
        val fakeSuccessResponse: Response<List<CountryDetail>> = Response.success(fakeResponse)
        val fakeDomainResult = listOf(
            CountryDomain(
                capital = "Bahamas, NA", code = "BS",
                name = "Nassau", region = "Bahamas"
            )
        )

        repo = RepositoryImpl(apiService)
        coEvery { apiService.getCountryList() } returns fakeSuccessResponse

        val responseFlow = repo.getCountryListFromApi().toList()
        advanceUntilIdle()

        assert(responseFlow[0] is Result.Loading)
        assert(responseFlow[1] is Result.Success)
        val successResult = responseFlow[1] as Result.Success
        assert(successResult.data == fakeDomainResult)
    }

    @Test
    fun `getCountryListFromApi returns error on network failure`() = runTest(testDispatcher) {
        val exception = IOException("Network error")
        coEvery { apiService.getCountryList() } throws exception

        repo = RepositoryImpl(apiService)

        val responseFlow = repo.getCountryListFromApi().toList()
        advanceUntilIdle()

        assert(responseFlow[0] is Result.Loading)
        assert(responseFlow[1] is Result.Error)
        val errorResult = responseFlow[1] as Result.Error
        assert(errorResult.exception is IOException)
    }

    @Test
    fun `getCountryListFromApi returns error on unsuccessful response`() = runTest(testDispatcher) {
        val fakeErrorResponse: Response<List<CountryDetail>> = Response.error(
            404, "Internal Server Error".toResponseBody(null)
        )

        coEvery { apiService.getCountryList() } returns fakeErrorResponse
        repo = RepositoryImpl(apiService)

        val responseFlow = repo.getCountryListFromApi().toList()
        advanceUntilIdle()

        assert(responseFlow[0] is Result.Loading)
        assert(responseFlow[1] is Result.Error)
        val errorResult = responseFlow[1] as Result.Error
        assert(errorResult.exception.message == "Network call failed: 404")
    }

    @Test
    fun `getCountryListFromApi returns error on empty response body`() = runTest(testDispatcher) {
        val fakeEmptyResponse: Response<List<CountryDetail>> = Response.success(null)

        coEvery { apiService.getCountryList() } returns fakeEmptyResponse
        repo = RepositoryImpl(apiService)

        val responseFlow = repo.getCountryListFromApi().toList()
        advanceUntilIdle()

        assert(responseFlow[0] is Result.Loading)
        assert(responseFlow[1] is Result.Error)
        val errorResult = responseFlow[1] as Result.Error
        assert(errorResult.exception.message == "Empty response body")
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}