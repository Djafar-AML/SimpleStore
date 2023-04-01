package com.example.simplestore.ui.fragments.productslistfragment.vm.util

import com.example.simplestore.model.domain.Filter
import com.example.simplestore.model.domain.Product
import javax.inject.Inject

class ProductListFilterGenerator @Inject constructor() {

    // todo test me
    operator fun invoke(productsList: List<Product>): Set<Filter> {

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