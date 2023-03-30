package com.example.simplestore.ui.fragments.productslistfragment.vm.util

import com.example.simplestore.model.domain.Filter
import com.example.simplestore.model.domain.Product
import com.example.simplestore.model.ui.UiProduct
import com.example.simplestore.redux.state.ApplicationState
import com.example.simplestore.redux.state.ProductFilterInfo
import javax.inject.Inject

class ProductsListStateUpdater @Inject constructor() {

    fun uiProducts(
        listOfProducts: List<Product>,
        setOfFavoriteIds: Set<Int>,
        setOfIsExpandedIds: Set<Int>
    ) = listOfProducts.map { product ->
        UiProduct(
            product = product,
            isFavorite = setOfFavoriteIds.contains(product.id),
            isExpanded = setOfIsExpandedIds.contains(product.id)
        )
    }

    fun updateProductListState(
        state: ApplicationState,
        productList: List<Product>,
        filters: Set<Filter>
    ): ApplicationState {

        return state.copy(
            productList = productList,
            productFilterInfo = ProductFilterInfo(filters = filters, selectedFilter = null)
        )
    }

    fun updateProductFilterListState(
        state: ApplicationState,
        filters: Set<Filter>,
    ): ApplicationState {

        return state.copy(productFilterInfo = ProductFilterInfo(filters))
    }

    fun updateFavoriteProductsIdsState(state: ApplicationState, id: Int): ApplicationState {

        val currentFavoriteIds = state.favoriteProductIds

        val newFavoriteIds = if (currentFavoriteIds.contains(id)) {
            currentFavoriteIds.filter { it != id }.toSet()
        } else {
            currentFavoriteIds + setOf(id)
        }

        return state.copy(favoriteProductIds = newFavoriteIds)
    }

    fun updateIsExpandedIdsState(state: ApplicationState, id: Int): ApplicationState {

        val currentIsExpandedIds = state.isExpandedProductIds

        val newIsExpandedIds = if (currentIsExpandedIds.contains(id)) {
            currentIsExpandedIds.filter { it != id }.toSet()
        } else {
            currentIsExpandedIds + setOf(id)
        }

        return state.copy(isExpandedProductIds = newIsExpandedIds)

    }

    fun updateProductFilterInfo(
        stateSnapshot: ApplicationState,
        currentlySelectedFilter: Filter?,
        filter: Filter
    ) = stateSnapshot.copy(
        productFilterInfo = stateSnapshot.productFilterInfo.copy(
            selectedFilter = if (currentlySelectedFilter != filter) filter else null
        )
    )

}