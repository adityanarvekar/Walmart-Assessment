package com.aditya.walmartassessment.data.services

import com.aditya.walmartassessment.data.dto.CountryDetail
import com.aditya.walmartassessment.data.utility.Constants.END_POINT
import retrofit2.Response
import retrofit2.http.GET

/**
 * interface to wrap call request to methods
 */
interface ApiService {
    @GET(END_POINT)
    suspend fun getCountryList(): Response<List<CountryDetail>>

    companion object {
        private lateinit var INSTANCE: ApiService
        fun getInstance(): ApiService {
            if (!::INSTANCE.isInitialized) {
                INSTANCE = ApiClient.retrofit.create(ApiService::class.java)
            }
            return INSTANCE
        }
    }
}