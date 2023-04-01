package com.example.simplestore.redux.update

import com.example.simplestore.model.domain.Filter
import com.example.simplestore.redux.state.ApplicationState
import com.example.simplestore.redux.state.ProductFilterInfo
import javax.inject.Inject

class UiProductFilterListUpdate @Inject constructor() {

    operator fun invoke(
        state: ApplicationState,
        filters: Set<Filter>,
    ): ApplicationState {

        return state.copy(productFilterInfo = ProductFilterInfo(filters))
    }

}