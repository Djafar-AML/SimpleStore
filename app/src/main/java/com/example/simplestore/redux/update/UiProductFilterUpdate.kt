package com.example.simplestore.redux.update

import com.example.simplestore.model.domain.Filter
import com.example.simplestore.redux.state.ApplicationState
import javax.inject.Inject

class UiProductFilterUpdate @Inject constructor() {

    operator fun invoke(
        stateSnapshot: ApplicationState,
        filter: Filter
    ): ApplicationState {

        val currentlySelectedFilter = stateSnapshot.productFilterInfo.selectedFilter

        return stateSnapshot.copy(
            productFilterInfo = stateSnapshot.productFilterInfo.copy(
                selectedFilter = if (currentlySelectedFilter != filter) filter else null
            )
        )
    }
}