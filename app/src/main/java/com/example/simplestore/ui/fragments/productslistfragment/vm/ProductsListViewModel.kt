package com.example.simplestore.ui.fragments.productslistfragment.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.simplestore.model.domain.Filter
import com.example.simplestore.model.domain.Product
import com.example.simplestore.model.ui.ProductsListFragmentUi
import com.example.simplestore.model.ui.UiFilter
import com.example.simplestore.model.ui.UiProduct
import com.example.simplestore.redux.state.ApplicationState
import com.example.simplestore.redux.state.ProductFilterInfo
import com.example.simplestore.redux.store.Store
import com.example.simplestore.ui.fragments.productslistfragment.repo.SharedRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsListViewModel @Inject constructor(
    private val store: Store<ApplicationState>,
    private val sharedRepo: SharedRepo
) : ViewModel() {

    val uiProductList = uiProductListLiveData()

    private fun uiProductListLiveData(): LiveData<ProductsListFragmentUi> {

        return combine(
            store.stateFlow.map { it.productList },
            store.stateFlow.map { it.favoriteProductIds },
            store.stateFlow.map { it.isExpandedProductIds },
            store.stateFlow.map { it.productFilterInfo },
        ) { listOfProducts, setOfFavoriteIds, setOfIsExpandedIds, productFilterInfo ->

            val uiProducts = uiProducts(listOfProducts, setOfFavoriteIds, setOfIsExpandedIds)

            val uiFilters = uiFilters(productFilterInfo)

            val filteredProducts =
                filterProductsInfo(uiProducts, productFilterInfo.selectedFilter)

            return@combine ProductsListFragmentUi(uiFilters, filteredProducts)

        }.distinctUntilChanged().asLiveData()

    }

    private fun uiProducts(
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

    private fun uiFilters(productFilterInfo: ProductFilterInfo) =
        productFilterInfo.filters.map { filter ->
            UiFilter(
                filter = filter,
                isSelected = productFilterInfo.selectedFilter?.equals(filter) == true
            )
        }.toSet()

    private fun filterProductsInfo(
        uiProducts: List<UiProduct>,
        selectedFilter: Filter?
    ) = if (selectedFilter == null) {
        uiProducts
    } else {
        uiProducts.filter { it.product.category == selectedFilter.value }
    }

    init {

        viewModelScope.launch {

            val productList = sharedRepo.productList()

            store.update { state ->
                val newState =
                    updateProductListState(state, productList)
                updateProductFilterListState(newState, productList)
            }

        }

    }

    private fun updateProductListState(
        state: ApplicationState,
        productList: List<Product>
    ): ApplicationState {
        return state.copy(productList = productList)
    }

    private fun updateProductFilterListState(
        state: ApplicationState,
        productList: List<Product>
    ): ApplicationState {

        val productFilterInfo = ProductFilterInfo(
            productList.map { Filter(value = it.category, displayText = it.category) }.toSet(),
            selectedFilter = null
        )

        return state.copy(productFilterInfo = productFilterInfo)
    }

    fun updateFavoriteIcon(id: Int) {

        viewModelScope.launch {
            store.update { state ->
                updateFavoriteProductsIdsState(state, id)
            }
        }

    }

    private fun updateFavoriteProductsIdsState(state: ApplicationState, id: Int): ApplicationState {

        val currentFavoriteIds = state.favoriteProductIds

        val newFavoriteIds = if (currentFavoriteIds.contains(id)) {
            currentFavoriteIds.filter { it != id }.toSet()
        } else {
            currentFavoriteIds + setOf(id)
        }

        return state.copy(favoriteProductIds = newFavoriteIds)
    }

    fun updateIsExpanded(id: Int) {

        viewModelScope.launch {
            store.update { state ->
                updateIsExpandedIdsState(state, id)
            }
        }

    }

    private fun updateIsExpandedIdsState(state: ApplicationState, id: Int): ApplicationState {

        val currentIsExpandedIds = state.isExpandedProductIds

        val newIsExpandedIds = if (currentIsExpandedIds.contains(id)) {
            currentIsExpandedIds.filter { it != id }.toSet()
        } else {
            currentIsExpandedIds + setOf(id)
        }

        return state.copy(isExpandedProductIds = newIsExpandedIds)

    }

    fun updateFilterSelection(filter: Filter) {

        viewModelScope.launch {

            store.update { stateSnapshot ->

                val currentlySelectedFilter = stateSnapshot.productFilterInfo.selectedFilter

                return@update stateSnapshot.copy(
                    productFilterInfo = stateSnapshot.productFilterInfo.copy(
                        selectedFilter = if (currentlySelectedFilter != filter) filter else null
                    )
                )
            }
        }
    }

}