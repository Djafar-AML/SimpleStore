package com.example.simplestore.ui.fragments.cart.epoxy.swipecallback

import android.view.View
import com.airbnb.epoxy.EpoxyTouchHelper
import com.example.simplestore.ui.fragments.cart.epoxy.model.CartItemEpoxyModel

class CartItemSwipeCallback(
    private val onProductItemSwipe: (Int) -> Unit
) : EpoxyTouchHelper.SwipeCallbacks<CartItemEpoxyModel>() {

    override fun onSwipeCompleted(
        model: CartItemEpoxyModel?,
        itemView: View?,
        position: Int,
        direction: Int
    ) {
        model?.let {
            val productId = model.uiProductInCart.uiProduct.product.id
            onProductItemSwipe(productId)
        }
    }
}