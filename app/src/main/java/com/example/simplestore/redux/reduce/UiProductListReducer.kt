package com.example.simplestore.redux.reduce

import com.example.simplestore.model.ui.UiProduct
import com.example.simplestore.redux.state.ApplicationState
import com.example.simplestore.redux.store.Store
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UiProductListReducer @Inject constructor() {

    fun reduce(store: Store<ApplicationState>): Flow<List<UiProduct>> {

        return combine(
            store.stateFlow.map { it.productList },
            store.stateFlow.map { it.favoriteProductIds },
            store.stateFlow.map { it.isExpandedProductIds },
            store.stateFlow.map { it.inCartProductIds },
        ) { listOfProducts, setOfFavoriteIds, setOfExpandedIds, inCartIds ->

            if (listOfProducts.isEmpty()) {
                return@combine emptyList<UiProduct>()
            }

            return@combine listOfProducts.map { product ->
                val id = product.id
                UiProduct(
                    product,
                    isFavorite = setOfFavoriteIds.contains(id),
                    isExpanded = setOfExpandedIds.contains(id),
                    isInCart = inCartIds.contains(id)
                )
            }

        }.distinctUntilChanged()

    }

}