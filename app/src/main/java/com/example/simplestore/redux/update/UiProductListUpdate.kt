package com.example.simplestore.redux.update

import com.example.simplestore.model.domain.Product
import com.example.simplestore.redux.state.ApplicationState
import javax.inject.Inject

class UiProductListUpdate @Inject constructor() {

    operator fun invoke(
        state: ApplicationState,
        productList: List<Product>,
    ): ApplicationState {

        return state.copy(productList = productList)
    }
}