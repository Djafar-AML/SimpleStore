package com.example.simplestore.redux.state

import com.example.simplestore.model.domain.Product

data class ApplicationState(
    val productList: List<Product> = emptyList(),
    val favoriteProductIds: Set<Int> = emptySet()
)
