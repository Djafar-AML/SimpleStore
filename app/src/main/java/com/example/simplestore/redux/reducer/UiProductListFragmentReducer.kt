package com.example.simplestore.redux.reducer

import com.example.simplestore.model.ui.ProductsListFragmentUi
import com.example.simplestore.redux.state.ApplicationState
import com.example.simplestore.redux.store.Store
import com.example.simplestore.ui.fragments.productslistfragment.vm.util.FilterGenerator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UiProductListFragmentReducer @Inject constructor(
    private val uiProductListReducer: UiProductListReducer,
) {

    fun reduce(
        store: Store<ApplicationState>,
        filterGenerator: FilterGenerator
    ): Flow<ProductsListFragmentUi> {

        return combine(
            uiProductListReducer.reduce(store),
            store.stateFlow.map { it.productFilterInfo },
        ) { uiProducts, productFilterInfo ->

            if (uiProducts.isEmpty()) {
                ProductsListFragmentUi.Loading
                return@combine ProductsListFragmentUi.Loading
            }

            val uiFilters = filterGenerator.uiFilters(productFilterInfo)

            val filteredProducts =
                filterGenerator.filterProductsInfo(uiProducts, productFilterInfo.selectedFilter)

            return@combine ProductsListFragmentUi.Success(uiFilters, filteredProducts)
        }
    }

}