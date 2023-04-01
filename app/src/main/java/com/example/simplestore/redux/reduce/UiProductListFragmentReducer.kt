package com.example.simplestore.redux.reduce

import com.example.simplestore.model.ui.ProductsListFragmentUi
import com.example.simplestore.redux.state.ApplicationState
import com.example.simplestore.redux.store.Store
import com.example.simplestore.ui.fragments.productslist.vm.util.ProductsListUiStateGenerator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UiProductListFragmentReducer @Inject constructor(
    private val uiProductListReducer: UiProductListReducer,
    private val productsListUiStateGenerator: ProductsListUiStateGenerator,
) {

    fun reduce(
        store: Store<ApplicationState>,
    ): Flow<ProductsListFragmentUi> {

        return combine(
            uiProductListReducer.reduce(store),
            store.stateFlow.map { it.productFilterInfo },
        ) { uiProducts, productFilterInfo ->
            return@combine productsListUiStateGenerator(uiProducts, productFilterInfo)
        }
    }

}