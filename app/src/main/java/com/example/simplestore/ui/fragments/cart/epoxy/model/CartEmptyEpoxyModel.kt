package com.example.simplestore.ui.fragments.cart.epoxy.model

import android.view.View
import com.example.simplestore.R
import com.example.simplestore.databinding.EpoxyModelCartEmptyBinding
import com.example.simplestore.epxoybinding.ViewBindingKotlinModel

data class CartEmptyEpoxyModel(
    private val onClick: (View) -> Unit
) : ViewBindingKotlinModel<EpoxyModelCartEmptyBinding>(R.layout.epoxy_model_cart_empty) {

    override fun EpoxyModelCartEmptyBinding.bind() {
        button.setOnClickListener(onClick)
    }
}