package com.example.simplestore.ui.fragments.productslistfragment.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.simplestore.model.domain.Filter
import com.example.simplestore.model.ui.ProductsListFragmentUi
import com.example.simplestore.redux.reducer.UiProductListFragmentReducer
import com.example.simplestore.redux.state.ApplicationState
import com.example.simplestore.redux.store.Store
import com.example.simplestore.ui.fragments.productslistfragment.repo.SharedRepo
import com.example.simplestore.ui.fragments.productslistfragment.vm.util.FilterGenerator
import com.example.simplestore.ui.fragments.productslistfragment.vm.util.ProductsListStateUpdater
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsListViewModel @Inject constructor(
    private val store: Store<ApplicationState>,
    private val sharedRepo: SharedRepo,
    private val filterGenerator: FilterGenerator,
    private val productsListStateUpdater: ProductsListStateUpdater,
    private val uiProductListFragmentReducer: UiProductListFragmentReducer,
) : ViewModel() {

    val uiProductList = uiProductListLiveData()

    private fun uiProductListLiveData(): LiveData<ProductsListFragmentUi> {

        return uiProductListFragmentReducer
            .reduce(store, filterGenerator)
            .distinctUntilChanged()
            .asLiveData()

    }

    init {

        viewModelScope.launch {

            val productList = sharedRepo.productList()

            val filters = filterGenerator.filterProductsList(productList)

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

                productsListStateUpdater.updateProductFilterInfo(stateSnapshot, filter)
            }
        }
    }

    fun updateOnAddToCart(id: Int) {

        viewModelScope.launch {

            store.update { stateSnapshot ->

                productsListStateUpdater.updateProductItemInCartIds(stateSnapshot, id)

            }
        }
    }

}