package com.example.simplestore.redux.update

import com.example.simplestore.redux.state.ApplicationState
import javax.inject.Inject

class ItemQuantityCountUpdate @Inject constructor() {

    operator fun invoke(
        stateSnapshot: ApplicationState,
        itemId: Int,
        quantity: Int
    ): ApplicationState {

        if (quantity < 1)
            return stateSnapshot

        val newMapEntry: Pair<Int, Int> = itemId to quantity
        val newMap: Map<Int, Int> = stateSnapshot.cartQuantitiesMap + newMapEntry

        return stateSnapshot.copy(cartQuantitiesMap = newMap)

    }

}