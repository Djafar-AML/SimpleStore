package com.example.simplestore.ui.fragments.productslist.epoxy.model

import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.example.simplestore.R
import com.example.simplestore.databinding.EpoxyModelProductItemBinding
import com.example.simplestore.epxoybinding.ViewBindingKotlinModel
import com.example.simplestore.extensions.loadByCoil
import com.example.simplestore.model.ui.UiProduct
import java.text.NumberFormat
import kotlin.math.roundToInt

data class ProductEpoxyModel(
    private val uiProduct: UiProduct,
    private val onFavoriteIconClick: (Int) -> Unit,
    private val onUiProductClick: (Int) -> Unit,
    private val onAddToCartClick: (Int) -> Unit,
) : ViewBindingKotlinModel<EpoxyModelProductItemBinding>(R.layout.epoxy_model_product_item) {

    private val currencyFormatter = NumberFormat.getCurrencyInstance()
    override fun EpoxyModelProductItemBinding.bind() {

        initViews()

        loadImage()

        setupClickListeners()

    }

    private fun EpoxyModelProductItemBinding.initViews() {

        productTitleTextView.text = uiProduct.product.title
        productCategoryTextView.text = uiProduct.product.category
        productDescriptionTextView.text = uiProduct.product.description
        productPriceTextView.text = currencyFormatter.format(uiProduct.product.price)
        productDescriptionTextView.isVisible = uiProduct.isExpanded
        inCartView.isVisible = uiProduct.isInCart

        val imageRes = imageRes(uiProduct.isFavorite)
        favoriteImageView.setIconResource(imageRes)

        ratingIndicator.progress = (uiProduct.product.rating.rateValue * 10).roundToInt()
        ratingTextView.text = "${uiProduct.product.rating.rateValue}"

    }

    private fun imageRes(isFavorite: Boolean) = if (isFavorite) {
        R.drawable.ic_round_favorite_24
    } else {
        R.drawable.ic_round_favorite_border_24
    }

    private fun EpoxyModelProductItemBinding.loadImage() {

        productImageViewLoadingProgressBar.isVisible = true

        val imageUrl = uiProduct.product.imageUrl

        productImageView.loadByCoil(imageUrl) { request, result ->
            productImageViewLoadingProgressBar.isGone = true
        }

    }

    private fun EpoxyModelProductItemBinding.setupClickListeners() {

        cardView.setOnClickListener {
            onUiProductClick(uiProduct.product.id)
        }

        addToCartButton.setOnClickListener {
            onAddToCartClick(uiProduct.product.id)
        }

        favoriteImageView.setOnClickListener {
            onFavoriteIconClick(uiProduct.product.id)
        }
    }

}
