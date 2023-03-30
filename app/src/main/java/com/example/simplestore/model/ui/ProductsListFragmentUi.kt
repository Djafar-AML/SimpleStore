package com.example.simplestore.model.ui

sealed class ProductsListFragmentUi {
    data class Success(
        val filters: Set<UiFilter>,
        val products: List<UiProduct>
    ) : ProductsListFragmentUi()

    object Loading : ProductsListFragmentUi()

}
