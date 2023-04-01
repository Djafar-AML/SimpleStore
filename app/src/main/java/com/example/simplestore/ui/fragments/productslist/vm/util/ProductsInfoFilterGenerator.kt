package com.example.simplestore.ui.fragments.productslist.vm.util

import com.example.simplestore.model.domain.Filter
import com.example.simplestore.model.ui.UiProduct
import javax.inject.Inject

class ProductsInfoFilterGenerator @Inject constructor() {

    operator fun invoke(
        uiProducts: List<UiProduct>,
        selectedFilter: Filter?
    ) = if (selectedFilter == null) {
        uiProducts
    } else {
        uiProducts.filter { it.product.category == selectedFilter.value }
    }

}