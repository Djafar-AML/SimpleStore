package com.example.simplestore.ui.fragments.cart.epoxy.controller

import com.airbnb.epoxy.TypedEpoxyController
import com.example.simplestore.epoxyutil.DividerEpoxyModel
import com.example.simplestore.epoxyutil.VerticalSpaceEpoxyModel
import com.example.simplestore.extensions.toPx
import com.example.simplestore.model.ui.CartFragmentUi
import com.example.simplestore.model.ui.UiProduct
import com.example.simplestore.ui.fragments.cart.epoxy.model.CartEmptyEpoxyModel
import com.example.simplestore.ui.fragments.cart.epoxy.model.CartItemEpoxyModel

class CartEpoxyController : TypedEpoxyController<CartFragmentUi>() {

    override fun buildModels(data: CartFragmentUi?) {

        when (data) {

            null, is CartFragmentUi.Empty -> {
                addEmptyModel()
            }
            is CartFragmentUi.Data -> {

                data.products.forEachIndexed { index, uiProduct ->
                    addVerticalStyling(index)
                    addCartItemModel(uiProduct)
                }

            }
        }
    }


    private fun addEmptyModel() {
        CartEmptyEpoxyModel(onClick = {
            // todo
        }).id("empty_state").addTo(this)
    }

    private fun addVerticalStyling(index: Int) {

        VerticalSpaceEpoxyModel(8.toPx()).id("space_before_$index").addTo(this)

        if (index != 0) {
            DividerEpoxyModel(horizontalMargin = 16.toPx()).id("divider_$index")
                .addTo(this)

            VerticalSpaceEpoxyModel(8.toPx()).id("space_after_$index").addTo(this)
        }

    }

    private fun addCartItemModel(uiProduct: UiProduct) {
        CartItemEpoxyModel(
            uiProduct = uiProduct,
            16.toPx(),
            onFavoriteClicked = {
                // todo
            },
            onDeleteClicked = {
                // todo
            }
        ).id(uiProduct.product.id).addTo(this)
    }

}