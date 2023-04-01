package com.example.simplestore.redux.update

import com.example.simplestore.redux.state.ApplicationState
import javax.inject.Inject

class UiProductFavoriteUpdate @Inject constructor() {

    operator fun invoke(state: ApplicationState, id: Int): ApplicationState {

        val currentFavoriteIds = state.favoriteProductIds

        val newFavoriteIds = if (currentFavoriteIds.contains(id)) {
            currentFavoriteIds - id
        } else {
            currentFavoriteIds + id
        }

        return state.copy(favoriteProductIds = newFavoriteIds)
    }

}