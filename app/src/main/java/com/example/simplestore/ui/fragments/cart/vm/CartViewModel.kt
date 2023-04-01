package com.example.simplestore.ui.fragments.cart.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.simplestore.model.ui.CartFragmentUi
import com.example.simplestore.redux.reduce.UiProductInCartReducer
import com.example.simplestore.redux.state.ApplicationState
import com.example.simplestore.redux.store.Store
import com.example.simplestore.redux.update.ItemQuantityCountUpdate
import com.example.simplestore.redux.update.UiProductFavoriteUpdate
import com.example.simplestore.redux.update.UiProductItemInCartUpdate
import com.example.simplestore.ui.fragments.cart.vm.util.UiProductInCartGenerator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val store: Store<ApplicationState>,
    private val productFavoriteUpdate: UiProductFavoriteUpdate,
    private val productItemInCartUpdate: UiProductItemInCartUpdate,
    private val uiProductInCartReducer: UiProductInCartReducer,
    private val itemQuantityCountUpdate: ItemQuantityCountUpdate,
    private val uiProductInCartGenerator: UiProductInCartGenerator,
) : ViewModel() {

    val uiCartProductListLiveData = uiCartProductListLiveData()

    private fun uiCartProductListLiveData(): LiveData<CartFragmentUi> {

        val uiProductInCartList = uiProductInCartReducer.reduce()

        return uiProductInCartGenerator(uiProductInCartList).distinctUntilChanged().asLiveData()

    }

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

    fun onQuantityChange(id: Int, quantity: Int) {

        viewModelScope.launch {

            store.update { appStateSnapshot ->
                itemQuantityCountUpdate(appStateSnapshot, id, quantity)
            }
        }

    }

}