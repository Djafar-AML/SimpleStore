package com.example.simplestore.model.domain

import java.math.BigDecimal


data class Product(
    val id: Int = 0,
    val title: String = "",
    val price: BigDecimal = BigDecimal.ZERO,
    val description: String = "",
    val category: String = "",
    val imageUrl: String = "",
    val rating: Rating = Rating(),
) {
    data class Rating(
        val rateValue: Double = 0.0,
        val numberOfRatings: Int = 0,
    )
}
