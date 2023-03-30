package com.example.simplestore.ui.fragments.productslistfragment.vm.util

import com.example.simplestore.model.domain.Filter
import com.example.simplestore.model.domain.Product
import javax.inject.Inject

class FilterGenerator @Inject constructor() {

    // todo test me
    fun generateFrom(productsList: List<Product>): Set<Filter> {

        return productsList
            .groupBy { it.category }
            .map { mapEntry ->
                Filter(
                    value = mapEntry.key,
                    displayText = "${mapEntry.key} (${mapEntry.value.size})"
                )
            }.toSet()
    }
}