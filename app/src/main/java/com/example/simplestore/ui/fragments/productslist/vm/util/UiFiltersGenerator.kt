package com.example.simplestore.ui.fragments.productslist.vm.util

import com.example.simplestore.model.ui.UiFilter
import com.example.simplestore.redux.state.ProductFilterInfo
import javax.inject.Inject

class UiFiltersGenerator @Inject constructor() {

    operator fun invoke(productFilterInfo: ProductFilterInfo) =
        productFilterInfo.filters.map { filter ->
            UiFilter(
                filter = filter,
                isSelected = productFilterInfo.selectedFilter?.equals(filter) == true
            )
        }.toSet()

}