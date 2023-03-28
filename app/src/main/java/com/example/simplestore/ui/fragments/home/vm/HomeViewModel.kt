package com.example.simplestore.ui.fragments.home.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplestore.extensions.asLiveData
import com.example.simplestore.model.domain.Product
import com.example.simplestore.ui.fragments.home.repo.SharedRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(sharedRepo: SharedRepo) : ViewModel() {


    private val _characterByIdLiveData = MutableLiveData<List<Product?>>()
    val characterByIdLiveData = _characterByIdLiveData.asLiveData()

    init {

        viewModelScope.launch {
            val productList = sharedRepo.productList()
            _characterByIdLiveData.postValue(productList)
        }

    }

}