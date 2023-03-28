package com.example.simplestore.ui.fragments.home.epoxy.controller

import com.airbnb.epoxy.TypedEpoxyController
import com.example.simplestore.model.domain.Product
import com.example.simplestore.ui.fragments.home.epoxy.model.ProductEpoxyModel

class ProductEpoxyController : TypedEpoxyController<List<Product?>>() {


    override fun buildModels(data: List<Product?>?) {

        if (data == null || data.isEmpty()) {
            // todo loading state
            return
        }

        data.forEach { product ->
            ProductEpoxyModel(product!!).id(product.id).addTo(this)
        }

    }

}