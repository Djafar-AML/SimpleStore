package com.example.simplestore.ui.fragments.home.epoxy.controller

import com.airbnb.epoxy.TypedEpoxyController
import com.example.simplestore.model.domain.Product
import com.example.simplestore.ui.fragments.home.epoxy.model.LoadingEpoxyModel
import com.example.simplestore.ui.fragments.home.epoxy.model.ProductEpoxyModel
import java.util.*

class ProductEpoxyController : TypedEpoxyController<List<Product?>>() {

    override fun buildModels(data: List<Product?>?) {

        if (data.isNullOrEmpty()) {
            LoadingEpoxyModel().id("loading_${UUID.randomUUID()}").addTo(this)
            return
        }

        data.forEach { product ->
            ProductEpoxyModel(product!!).id(product.id).addTo(this)
        }

    }

}