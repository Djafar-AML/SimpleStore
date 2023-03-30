package com.example.simplestore.redux.state

import com.example.simplestore.model.domain.Product

data class ApplicationState(
    val productList: List<Product> = emptyList(),
    val productFilterInfo: ProductFilterInfo = ProductFilterInfo(),
    val favoriteProductIds: Set<Int> = emptySet(),
    val isExpandedProductIds: Set<Int> = emptySet(),
    val inCartProductIds: Set<Int> = emptySet(),
)
