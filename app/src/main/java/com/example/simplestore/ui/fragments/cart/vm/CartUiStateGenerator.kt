package com.example.simplestore.ui.fragments.cart.vm

import com.example.simplestore.model.ui.CartFragmentUi
import com.example.simplestore.model.ui.UiProduct
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CartUiStateGenerator @Inject constructor() {

    operator fun invoke(productsInCartFlow: Flow<List<UiProduct>>): Flow<CartFragmentUi> {

        return productsInCartFlow.map {

            if (it.isEmpty()) {
                CartFragmentUi.Empty
            } else {
                CartFragmentUi.Data(it)
            }
        }
    }
}