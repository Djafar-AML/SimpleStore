package com.example.simplestore.ui.fragments.home.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.simplestore.model.domain.Product
import com.example.simplestore.model.ui.UiProduct
import com.example.simplestore.redux.state.ApplicationState
import com.example.simplestore.redux.store.Store
import com.example.simplestore.ui.fragments.home.repo.SharedRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val store: Store<ApplicationState>,
    private val sharedRepo: SharedRepo
) : ViewModel() {

    val uiProductList: LiveData<List<UiProduct>> = uiProductListLiveData()

    private fun uiProductListLiveData(): LiveData<List<UiProduct>> {

        return combine(
            store.stateFlow.map { it.productList },
            store.stateFlow.map { it.favoriteProductIds },
            store.stateFlow.map { it.isExpandedProductIds },
        ) { listOfProducts, setOfFavoriteIds, setOfIsExpandedIds ->

            listOfProducts.map { product ->
                UiProduct(
                    product = product,
                    isFavorite = setOfFavoriteIds.contains(product.id),
                    isExpanded = setOfIsExpandedIds.contains(product.id)
                )
            }
        }.distinctUntilChanged().asLiveData()

    }

    init {

        viewModelScope.launch {

            val productList = sharedRepo.productList()

            store.update { state ->
                updateProductListState(state, productList)
            }

        }

    }

    private fun updateProductListState(
        state: ApplicationState,
        productList: List<Product>
    ): ApplicationState {
        return state.copy(productList = productList)
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

}