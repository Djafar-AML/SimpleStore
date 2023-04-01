package com.example.simplestore.ui.fragments.cart.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.simplestore.model.ui.CartFragmentUi
import com.example.simplestore.redux.reduce.UiProductListReducer
import com.example.simplestore.redux.state.ApplicationState
import com.example.simplestore.redux.store.Store
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val store: Store<ApplicationState>,
    private val uiProductListReducer: UiProductListReducer,
) : ViewModel() {


    val uiCartProductListLiveData = uiCartProductListLiveData()

    private fun uiCartProductListLiveData(): LiveData<CartFragmentUi> {

        return productsInCartFlow().map {

            if (it.isEmpty()) {
                CartFragmentUi.Empty
            } else {
                CartFragmentUi.Data(it)
            }

        }.distinctUntilChanged().asLiveData()

    }

    private fun productsInCartFlow() = uiProductListReducer.reduce(store)
        .map { uiProducts -> uiProducts.filter { it.isInCart } }

}