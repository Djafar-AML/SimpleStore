package com.example.simplestore.ui.fragments.productslistfragment.vm.util

import com.example.simplestore.model.domain.Filter
import com.example.simplestore.model.domain.Product
import com.example.simplestore.model.ui.UiFilter
import com.example.simplestore.model.ui.UiProduct
import com.example.simplestore.redux.state.ProductFilterInfo
import javax.inject.Inject

class FilterGenerator @Inject constructor() {

    // todo test me
    fun filterProductsList(productsList: List<Product>): Set<Filter> {

        return productsList
            .groupBy { it.category }
            .map { mapEntry ->
                Filter(
                    value = mapEntry.key,
                    displayText = "${mapEntry.key} (${mapEntry.value.size})"
                )
            }.toSet()
    }

    fun filterProductsInfo(
        uiProducts: List<UiProduct>,
        selectedFilter: Filter?
    ) = if (selectedFilter == null) {
        uiProducts
    } else {
        uiProducts.filter { it.product.category == selectedFilter.value }
    }

    fun uiFilters(productFilterInfo: ProductFilterInfo) =
        productFilterInfo.filters.map { filter ->
            UiFilter(
                filter = filter,
                isSelected = productFilterInfo.selectedFilter?.equals(filter) == true
            )
        }.toSet()

}