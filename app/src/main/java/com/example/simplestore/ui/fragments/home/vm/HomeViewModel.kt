package com.example.simplestore.ui.fragments.home.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.simplestore.model.domain.Product
import com.example.simplestore.redux.state.ApplicationState
import com.example.simplestore.redux.store.Store
import com.example.simplestore.ui.fragments.home.repo.SharedRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val store: Store<ApplicationState>,
    private val sharedRepo: SharedRepo
) : ViewModel() {


    val productListLiveData: LiveData<List<Product>> =
        store.stateFlow.map { it.productList }.asLiveData()

    init {

        viewModelScope.launch {

            val productList = sharedRepo.productList()

            store.update { applicationState ->
                updateProductList(applicationState, productList)
            }

        }

    }

    private fun updateProductList(
        state: ApplicationState,
        productList: List<Product>
    ): ApplicationState {
        return state.copy(productList = productList)
    }

}