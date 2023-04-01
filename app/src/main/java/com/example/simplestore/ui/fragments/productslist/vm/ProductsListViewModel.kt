package com.example.simplestore.ui.fragments.productslist.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.simplestore.model.domain.Filter
import com.example.simplestore.model.ui.ProductsListFragmentUi
import com.example.simplestore.redux.reduce.UiProductListFragmentReducer
import com.example.simplestore.redux.state.ApplicationState
import com.example.simplestore.redux.store.Store
import com.example.simplestore.redux.update.*
import com.example.simplestore.ui.fragments.productslist.repo.SharedRepo
import com.example.simplestore.ui.fragments.productslist.vm.util.ProductListFilterGenerator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsListViewModel @Inject constructor(
    private val store: Store<ApplicationState>,
    private val sharedRepo: SharedRepo,
    private val productListFilterGenerator: ProductListFilterGenerator,
    private val uiProductListFragmentReducer: UiProductListFragmentReducer,
    private val productFavoriteUpdate: UiProductFavoriteUpdate,
    private val productItemInCartUpdate: UiProductItemInCartUpdate,
    private val productFilterUpdate: UiProductFilterUpdate,
    private val productExpansionUpdate: UiProductExpansionUpdate,
    private val productListUpdate: UiProductListUpdate,
    private val productFilterListUpdate: UiProductFilterListUpdate,
) : ViewModel() {

    val uiProductList = uiProductListLiveData()

    private fun uiProductListLiveData(): LiveData<ProductsListFragmentUi> {

        return uiProductListFragmentReducer
            .reduce()
            .distinctUntilChanged()
            .asLiveData()
    }

    init {

        viewModelScope.launch {

            val productList = sharedRepo.productList()

            val filters = productListFilterGenerator(productList)

            store.update { state ->
                val newState = productListUpdate(state, productList)
                productFilterListUpdate(newState, filters)
            }
        }

    }

    fun updateFavoriteIcon(id: Int) {

        viewModelScope.launch {

            store.update { state ->
                productFavoriteUpdate(state, id)
            }
        }
    }

    fun updateIsExpanded(id: Int) {

        viewModelScope.launch {

            store.update { state ->
                productExpansionUpdate(state, id)
            }
        }
    }

    fun updateFilterSelection(filter: Filter) {

        viewModelScope.launch {

            store.update { stateSnapshot ->
                productFilterUpdate(stateSnapshot, filter)
            }
        }
    }

    fun updateOnAddToCart(id: Int) {

        viewModelScope.launch {

            store.update { stateSnapshot ->
                productItemInCartUpdate(stateSnapshot, id)
            }
        }
    }

}