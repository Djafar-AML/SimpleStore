package com.example.simplestore.ui.fragments.cart.vm.util

import com.example.simplestore.model.ui.CartFragmentUi
import com.example.simplestore.model.ui.UiProductInCart
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UiProductInCartGenerator @Inject constructor() {

    operator fun invoke(productsInCartFlow: Flow<List<UiProductInCart>>): Flow<CartFragmentUi> {

        return productsInCartFlow.map {

            if (it.isEmpty()) {
                CartFragmentUi.Empty
            } else {
                CartFragmentUi.Data(it)
            }

        }.distinctUntilChanged()

    }

}