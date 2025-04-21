package com.aditya.walmartassessment.data.services

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

/**
 * Handle Api calls and track response states.
 *
 */
fun <T, R> result(
    call: suspend () -> Response<T>,
    mapper: (T) -> R
): Flow<com.aditya.walmartassessment.data.utility.Result<R>> = flow {
    emit(com.aditya.walmartassessment.data.utility.Result.Loading)
    try {
        val response = call()
        if (response.isSuccessful) {
            response.body()?.let {
                // data received
                emit(com.aditya.walmartassessment.data.utility.Result.Success(mapper(it)))
            } ?: emit(com.aditya.walmartassessment.data.utility.Result.Error(Exception("Empty response body")))
        } else {
            // error state
            emit(com.aditya.walmartassessment.data.utility.Result.Error(Exception("Network call failed: ${response.code()}")))
        }
    } catch (e: Exception) {
        // exception handling
        emit(com.aditya.walmartassessment.data.utility.Result.Error(e))
    }
}.flowOn(Dispatchers.IO)