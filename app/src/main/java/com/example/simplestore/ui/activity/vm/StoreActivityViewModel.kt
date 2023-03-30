package com.example.simplestore.ui.activity.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.simplestore.redux.state.ApplicationState
import com.example.simplestore.redux.store.Store
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class StoreActivityViewModel @Inject constructor(
    store: Store<ApplicationState>,
) : ViewModel() {

    val inCartProductCountLiveData =
        store.stateFlow.map { it.inCartProductIds.size }.distinctUntilChanged().asLiveData()

}