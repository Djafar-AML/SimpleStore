package com.example.simplestore.ui.fragments.cart.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.simplestore.model.ui.CartFragmentUi
import com.example.simplestore.redux.reduce.UiProductListReducer
import com.example.simplestore.redux.state.ApplicationState
import com.example.simplestore.redux.store.Store
import com.example.simplestore.redux.update.UiProductFavoriteUpdate
import com.example.simplestore.redux.update.UiProductItemInCartUpdate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val store: Store<ApplicationState>,
    private val uiProductListReducer: UiProductListReducer,
    private val productFavoriteUpdate: UiProductFavoriteUpdate,
    private val productItemInCartUpdate: UiProductItemInCartUpdate,
    private val cartUiStateGenerator: CartUiStateGenerator,
) : ViewModel() {


    val uiCartProductListLiveData = uiCartProductListLiveData()

    private fun uiCartProductListLiveData(): LiveData<CartFragmentUi> {

        return cartUiStateGenerator(productsInCartFlow()).distinctUntilChanged().asLiveData()
    }

    private fun productsInCartFlow() = uiProductListReducer.reduce(store)
        .map { uiProducts -> uiProducts.filter { it.isInCart } }


    fun onFavoriteClick(id: Int) {

        viewModelScope.launch {

            store.update { appStateSnapshot ->
                productFavoriteUpdate(appStateSnapshot, id)
            }
        }
    }

    fun onDeleteClick(id: Int) {

        viewModelScope.launch {

            store.update { appStateSnapshot ->
                productItemInCartUpdate(appStateSnapshot, id)
            }
        }
    }

}