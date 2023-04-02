package com.example.simplestore.ui.fragments.cart.epoxy.model

import android.view.ViewGroup
import androidx.annotation.Dimension
import androidx.core.view.updateLayoutParams
import coil.load
import com.example.simplestore.R
import com.example.simplestore.databinding.EpoxyModelCartProductItemBinding
import com.example.simplestore.epxoybinding.ViewBindingKotlinModel
import com.example.simplestore.model.ui.UiProductInCart

data class CartItemEpoxyModel(
    val uiProductInCart: UiProductInCart,
    @Dimension(unit = Dimension.PX) private val horizontalMargin: Int,
    private val onFavoriteClick: (Int) -> Unit,
    private val onDeleteClick: (Int) -> Unit,
    private val onQuantityChange: (Int, Int) -> Unit,
) : ViewBindingKotlinModel<EpoxyModelCartProductItemBinding>(R.layout.epoxy_model_cart_product_item) {

    override fun EpoxyModelCartProductItemBinding.bind() {

        setupViews()
        setupClickListeners()
        updateLayoutParams()

    }

    private fun EpoxyModelCartProductItemBinding.setupViews() {

        productTitleTextView.text = uiProductInCart.uiProduct.product.title

        productImageView.load(data = uiProductInCart.uiProduct.product.imageUrl)

        favoriteImageView.setIconResource(imageRes())

        quantityView.quantityTextView.text = uiProductInCart.quantity.toString()

    }

    private fun imageRes() = if (uiProductInCart.uiProduct.isFavorite) {
        R.drawable.ic_round_favorite_24
    } else {
        R.drawable.ic_round_favorite_border_24
    }

    private fun EpoxyModelCartProductItemBinding.setupClickListeners() {

        val productId = uiProductInCart.uiProduct.product.id

        favoriteImageView.setOnClickListener { onFavoriteClick(productId) }
        deleteIconImageView.setOnClickListener { onDeleteClick(productId) }

        quantityView.apply {

            minusImageView.setOnClickListener {
                onQuantityChange(
                    productId,
                    uiProductInCart.quantity - 1
                )
            }

            plusImageView.setOnClickListener {
                onQuantityChange(
                    productId,
                    uiProductInCart.quantity + 1
                )
            }

        }

    }

    private fun EpoxyModelCartProductItemBinding.updateLayoutParams() {

        root.updateLayoutParams<ViewGroup.MarginLayoutParams> {
            setMargins(horizontalMargin, 0, horizontalMargin, 0)
        }

    }

}