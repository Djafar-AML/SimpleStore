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
import kotlinx.coroutines.delay
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
            store.stateFlow.map { it.favoriteProductIds }
        ) { listOfProducts, setOfFavoriteIds ->

            listOfProducts.map { product ->
                UiProduct(product = product, isFavorite = setOfFavoriteIds.contains(product.id))
            }
        }.distinctUntilChanged().asLiveData()

    }

    init {

        viewModelScope.launch {

            val productList = sharedRepo.productList()

            store.update { state ->
                updateProductList(state, productList)
            }

            delay(5_000)

            store.update { state ->
                return@update state.copy(favoriteProductIds = setOf(1, 2, 4, 15, 16))
            }

        }

    }

    private fun updateProductList(
        state: ApplicationState,
        productList: List<Product>
    ): ApplicationState {
        return state.copy(productList = productList)
    }

}