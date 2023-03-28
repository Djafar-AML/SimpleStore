package com.example.simplestore.ui.fragments.home.epoxy.model

import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.example.simplestore.R
import com.example.simplestore.databinding.EpoxyModelProductItemBinding
import com.example.simplestore.epxoybinding.ViewBindingKotlinModel
import com.example.simplestore.extensions.loadByCoil
import com.example.simplestore.model.domain.Product

data class ProductEpoxyModel(
    private val product: Product
) : ViewBindingKotlinModel<EpoxyModelProductItemBinding>(R.layout.epoxy_model_product_item) {

    override fun EpoxyModelProductItemBinding.bind() {

        loadImage()

        setupClickListeners()

        productTitleTextView.text = product.title
        productCategoryTextView.text = product.category
        productDescriptionTextView.text = product.description
        productPriceTextView.text = String.format(product.price.toPlainString())


    }

    private fun EpoxyModelProductItemBinding.loadImage() {

        productImageViewLoadingProgressBar.isVisible = true

        val imageUrl = product.imageUrl

        productImageView.loadByCoil(imageUrl) { request, result ->
            productImageViewLoadingProgressBar.isGone = true
        }

    }

    private fun EpoxyModelProductItemBinding.setupClickListeners() {

        cardView.setOnClickListener {
            productDescriptionTextView.apply {
                isVisible = !isVisible
            }
        }

        addToCartButton.setOnClickListener {
            inCartView.apply {
                isVisible = !isVisible
            }
        }

        var isFavorite = false
        favoriteImageView.setOnClickListener {
            val imageRes = if (isFavorite) {
                R.drawable.ic_round_favorite_border_24
            } else {
                R.drawable.ic_round_favorite_24
            }

            favoriteImageView.setIconResource(imageRes)
            isFavorite = !isFavorite
        }

    }

}
