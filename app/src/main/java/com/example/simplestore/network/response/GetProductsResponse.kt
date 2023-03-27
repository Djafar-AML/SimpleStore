package com.example.simplestore.network.response

class GetProductsResponse : ArrayList<GetProductsResponse.GetProductsResponseItem>() {

    data class GetProductsResponseItem(
        val category: String = "",
        val description: String = "",
        val id: Int = 0,
        val image: String = "",
        val price: Double = 0.0,
        val rating: Rating = Rating(),
        val title: String = ""
    ) {
        data class Rating(
            val count: Int = 0,
            val rate: Double = 0.0
        )
    }
}