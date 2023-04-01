package com.example.simplestore.redux.reduce

import com.example.simplestore.model.ui.UiProduct
import com.example.simplestore.model.ui.UiProductInCart
import com.example.simplestore.redux.state.ApplicationState
import com.example.simplestore.redux.store.Store
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UiProductInCartReducer @Inject constructor(
    private val store: Store<ApplicationState>,
    private val uiProductListReducer: UiProductListReducer,
) {

    fun reduce(): Flow<List<UiProductInCart>> {

        return combine(
            productsInCartFlow(),
            store.stateFlow.map { it.cartQuantitiesMap }
        ) { uiProducts, quantityMap ->

            uiProducts.map {
                UiProductInCart(it, quantity = quantityMap[it.product.id] ?: 1)
            }
        }.distinctUntilChanged()

    }

    private fun productsInCartFlow(): Flow<List<UiProduct>> = uiProductListReducer.reduce(store)
        .map { uiProducts -> uiProducts.filter { it.isInCart } }.distinctUntilChanged()

}
