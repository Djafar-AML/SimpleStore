package com.example.simplestore.ui.fragments.home.repo

import com.example.simplestore.model.domain.Product
import com.example.simplestore.model.mapper.ProductMapper
import com.example.simplestore.network.ApiClient
import com.example.simplestore.model.network.GetProductResponse

class SharedRepo constructor(
    private val apiClient: ApiClient
) {

    suspend fun productList(): List<Product> {

        val response = apiClient.productListPage()

        if (response.failed || response.isSuccessful.not()) {
            // todo handle error
            return emptyList()
        }

        val products: List<Product> = response.body.map {
            ProductMapper.buildFrom(it)
        }

        return products

    }

}