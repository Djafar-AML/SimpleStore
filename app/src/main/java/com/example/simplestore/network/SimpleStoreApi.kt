package com.example.simplestore.network

import com.example.simplestore.model.network.GetProductResponse
import retrofit2.Response
import retrofit2.http.GET

interface SimpleStoreApi {

    @GET("products")
    suspend fun productList(): Response<List<GetProductResponse>>

}