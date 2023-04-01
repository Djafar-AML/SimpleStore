package com.example.simplestore.redux.update

import com.example.simplestore.redux.state.ApplicationState
import javax.inject.Inject

class UiProductExpansionUpdate @Inject constructor() {

    operator fun invoke(state: ApplicationState, id: Int): ApplicationState {

        val currentIsExpandedIds = state.isExpandedProductIds

        val newIsExpandedIds = if (currentIsExpandedIds.contains(id)) {
            currentIsExpandedIds - id
        } else {
            currentIsExpandedIds + id
        }

        return state.copy(isExpandedProductIds = newIsExpandedIds)

    }
}