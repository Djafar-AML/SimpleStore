package com.example.simplestore.ui.fragments.home.epoxy.model

import com.example.simplestore.R
import com.example.simplestore.databinding.ModelLoadingBinding
import com.example.simplestore.epxoybinding.ViewBindingKotlinModel

class LoadingEpoxyModel : ViewBindingKotlinModel<ModelLoadingBinding>(R.layout.model_loading) {

    override fun ModelLoadingBinding.bind() {
        // nothing to do
    }

    override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int): Int {
        return totalSpanCount
    }

}