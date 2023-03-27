package com.example.simplestore.ui.fragments.home.repo

import com.example.simplestore.network.ApiClient
import com.example.simplestore.network.response.GetProductsResponse

class SharedRepo constructor(
    private val apiClient: ApiClient
) {

    suspend fun productList(): GetProductsResponse? {

        val response = apiClient.productListPage()

        if (response.failed || response.isSuccessful.not()) {
            return null
        }

        return response.body

    }

}