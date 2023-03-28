package com.example.simplestore.model.mapper

import com.example.simplestore.model.domain.Product
import com.example.simplestore.model.network.GetProductResponse
import java.math.BigDecimal
import java.math.RoundingMode

object ProductMapper {

    fun buildFrom(getProductResponse: GetProductResponse): Product {

        return Product(
            getProductResponse.id,
            getProductResponse.title,
            BigDecimal(getProductResponse.price).setScale(
                2,
                RoundingMode.HALF_UP
            ), // 0.128 --> 0.13
            getProductResponse.description,
            getProductResponse.category,
            getProductResponse.image,
        )

    }

}