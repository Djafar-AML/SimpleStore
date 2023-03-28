package com.example.simplestore.network

import com.example.simplestore.model.network.GetProductResponse
import retrofit2.Response
import javax.inject.Inject

class ApiClient @Inject constructor(
    private val simpleStoreApi: SimpleStoreApi
) {

    suspend fun productListPage(): SimpleResponse<List<GetProductResponse>> {
        return safeApiCall { simpleStoreApi.productList() }
    }

    private inline fun <T> safeApiCall(apiCall: () -> Response<T>): SimpleResponse<T> {

        return try {
            SimpleResponse.success(apiCall.invoke())
        } catch (e: Exception) {
            SimpleResponse.failure(e)
        }

    }

}