package com.example.simplestore.ui.fragments.cart.epoxy.controller

import com.airbnb.epoxy.TypedEpoxyController
import com.example.simplestore.epoxyutil.DividerEpoxyModel
import com.example.simplestore.epoxyutil.VerticalSpaceEpoxyModel
import com.example.simplestore.extensions.toPx
import com.example.simplestore.model.ui.CartFragmentUi
import com.example.simplestore.model.ui.UiProductInCart
import com.example.simplestore.ui.fragments.cart.epoxy.model.CartEmptyEpoxyModel
import com.example.simplestore.ui.fragments.cart.epoxy.model.CartItemEpoxyModel

class CartEpoxyController(
    private val onFavoriteClick: (Int) -> Unit,
    private val onDeleteClick: (Int) -> Unit,
    private val onEmptyStateClick: () -> Unit,
    private val onQuantityChange: (Int, Int) -> Unit,
) : TypedEpoxyController<CartFragmentUi>() {

    override fun buildModels(data: CartFragmentUi?) {

        when (data) {

            null, is CartFragmentUi.Empty -> {
                addEmptyModel()
            }
            is CartFragmentUi.Data -> {

                data.products.forEachIndexed { index, uiProductInCart ->
                    addVerticalStyling(index)
                    addCartItemModel(uiProductInCart)
                }

            }
        }
    }


    private fun addEmptyModel() {
        CartEmptyEpoxyModel(onEmptyStateClick).id("empty_state").addTo(this)
    }

    private fun addVerticalStyling(index: Int) {

        VerticalSpaceEpoxyModel(8.toPx()).id("space_before_$index").addTo(this)

        if (index != 0) {
            DividerEpoxyModel(horizontalMargin = 16.toPx()).id("divider_$index")
                .addTo(this)

            VerticalSpaceEpoxyModel(8.toPx()).id("space_after_$index").addTo(this)
        }

    }

    private fun addCartItemModel(uiProductInCart: UiProductInCart) {

        CartItemEpoxyModel(
            uiProductInCart = uiProductInCart,
            16.toPx(),
            onFavoriteClick = onFavoriteClick,
            onDeleteClick = onDeleteClick,
            onQuantityChange = onQuantityChange
        ).id(uiProductInCart.uiProduct.product.id).addTo(this)

    }

}