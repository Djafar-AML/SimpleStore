package com.example.simplestore.model.ui

sealed class CartFragmentUi {
    object Empty : CartFragmentUi()
    data class Data(val products: List<UiProductInCart>) : CartFragmentUi()
}