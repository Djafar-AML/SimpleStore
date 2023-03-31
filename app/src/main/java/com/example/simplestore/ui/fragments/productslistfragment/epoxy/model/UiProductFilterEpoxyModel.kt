package com.example.simplestore.ui.fragments.productslistfragment.epoxy.model

import com.example.simplestore.R
import com.example.simplestore.databinding.EpoxyModelProductFilterBinding
import com.example.simplestore.epxoybinding.ViewBindingKotlinModel
import com.example.simplestore.extensions.setCustomBackgroundColor
import com.example.simplestore.model.domain.Filter
import com.example.simplestore.model.ui.UiFilter

data class UiProductFilterEpoxyModel(
    val uiFilter: UiFilter,
    val onFilterSelect: (Filter) -> Unit,
) : ViewBindingKotlinModel<EpoxyModelProductFilterBinding>(R.layout.epoxy_model_product_filter) {

    override fun EpoxyModelProductFilterBinding.bind() {

        filterNameTextView.text = uiFilter.filter.displayText

        val backgroundColorResId = backgroundColor(uiFilter.isSelected)
        root.setCustomBackgroundColor(backgroundColorResId)

        root.setOnClickListener {
            throw RuntimeException("Hi Firebase!")
            onFilterSelect(uiFilter.filter) }
    }

    private fun backgroundColor(isSelected: Boolean): Int {

        return if (isSelected) {
            R.color.purple_500
        } else {
            R.color.purple_200
        }
    }

}
