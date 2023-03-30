package com.example.simplestore.ui.fragments.productslistfragment.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.simplestore.model.domain.Filter
import com.example.simplestore.model.ui.ProductsListFragmentUi
import com.example.simplestore.model.ui.UiFilter
import com.example.simplestore.model.ui.UiProduct
import com.example.simplestore.redux.state.ApplicationState
import com.example.simplestore.redux.state.ProductFilterInfo
import com.example.simplestore.redux.store.Store
import com.example.simplestore.ui.fragments.productslistfragment.repo.SharedRepo
import com.example.simplestore.ui.fragments.productslistfragment.vm.util.FilterGenerator
import com.example.simplestore.ui.fragments.productslistfragment.vm.util.ProductsListStateUpdater
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsListViewModel @Inject constructor(
    private val store: Store<ApplicationState>,
    private val sharedRepo: SharedRepo,
    private val filterGenerator: FilterGenerator,
    private val productsListStateUpdater: ProductsListStateUpdater,
) : ViewModel() {

    val uiProductList = uiProductListLiveData()

    private fun uiProductListLiveData(): LiveData<ProductsListFragmentUi> {

        return combine(
            store.stateFlow.map { it.productList },
            store.stateFlow.map { it.favoriteProductIds },
            store.stateFlow.map { it.isExpandedProductIds },
            store.stateFlow.map { it.productFilterInfo },
        ) { listOfProducts, setOfFavoriteIds, setOfIsExpandedIds, productFilterInfo ->

            if (listOfProducts.isEmpty()) {
                ProductsListFragmentUi.Loading
                return@combine ProductsListFragmentUi.Loading
            }

            val uiProducts = productsListStateUpdater.uiProducts(
                listOfProducts,
                setOfFavoriteIds,
                setOfIsExpandedIds
            )

            val uiFilters = uiFilters(productFilterInfo)

            val filteredProducts =
                filterProductsInfo(uiProducts, productFilterInfo.selectedFilter)

            return@combine ProductsListFragmentUi.Success(uiFilters, filteredProducts)

        }.distinctUntilChanged().asLiveData()

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

            val filters = filterGenerator.generateFrom(productList)

            store.update { state ->
                val newState =
                    productsListStateUpdater.updateProductListState(state, productList, filters)
                productsListStateUpdater.updateProductFilterListState(newState, filters)
            }

        }

    }

    fun updateFavoriteIcon(id: Int) {

        viewModelScope.launch {
            store.update { state ->
                productsListStateUpdater.updateFavoriteProductsIdsState(state, id)
            }
        }

    }

    fun updateIsExpanded(id: Int) {

        viewModelScope.launch {
            store.update { state ->
                productsListStateUpdater.updateIsExpandedIdsState(state, id)
            }
        }

    }

    fun updateFilterSelection(filter: Filter) {

        viewModelScope.launch {

            store.update { stateSnapshot ->

                val currentlySelectedFilter = stateSnapshot.productFilterInfo.selectedFilter

                productsListStateUpdater.updateProductFilterInfo(
                    stateSnapshot,
                    currentlySelectedFilter,
                    filter
                )
            }
        }
    }

}