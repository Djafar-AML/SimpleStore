package com.example.simplestore.ui.fragments.productslist.vm.util

import com.example.simplestore.model.ui.ProductsListFragmentUi
import com.example.simplestore.model.ui.UiProduct
import com.example.simplestore.redux.state.ProductFilterInfo
import javax.inject.Inject

class ProductsListUiStateGenerator @Inject constructor(
    private val uiFiltersGenerator: UiFiltersGenerator,
    private val productsInfoFilterGenerator: ProductsInfoFilterGenerator,
) {

    operator fun invoke(
        uiProducts: List<UiProduct>,
        productFilterInfo: ProductFilterInfo
    ): ProductsListFragmentUi {

        if (uiProducts.isEmpty()) {
            ProductsListFragmentUi.Loading
            return ProductsListFragmentUi.Loading
        }

        val uiFilters = uiFiltersGenerator(productFilterInfo)

        val filteredProducts =
            productsInfoFilterGenerator(uiProducts, productFilterInfo.selectedFilter)

        return ProductsListFragmentUi.Success(uiFilters, filteredProducts)

    }

}