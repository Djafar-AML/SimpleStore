package com.example.simplestore.model.ui

import com.example.simplestore.model.domain.Product

data class UiProduct(
    val product: Product,
    val isFavorite: Boolean = false
)
