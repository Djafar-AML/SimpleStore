package com.example.simplestore.redux.update

import com.example.simplestore.redux.state.ApplicationState
import javax.inject.Inject

class UiProductItemInCartUpdate @Inject constructor() {

    operator fun invoke(stateSnapshot: ApplicationState, id: Int): ApplicationState {

        val currentProductIdsInCart = stateSnapshot.inCartProductIds

        val newProductIdsInCart = if (currentProductIdsInCart.contains(id)) {
            currentProductIdsInCart - id
        } else {
            currentProductIdsInCart + id
        }

        return stateSnapshot.copy(inCartProductIds = newProductIdsInCart)

    }

}