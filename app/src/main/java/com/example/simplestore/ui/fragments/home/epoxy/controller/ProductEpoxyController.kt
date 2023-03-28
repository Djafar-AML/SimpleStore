package com.example.simplestore.ui.fragments.home.epoxy.controller

import com.airbnb.epoxy.TypedEpoxyController
import com.example.simplestore.model.ui.UiProduct
import com.example.simplestore.ui.fragments.home.epoxy.model.LoadingEpoxyModel
import com.example.simplestore.ui.fragments.home.epoxy.model.ProductEpoxyModel
import java.util.*

class ProductEpoxyController : TypedEpoxyController<List<UiProduct?>>() {

    override fun buildModels(data: List<UiProduct?>?) {

        if (data.isNullOrEmpty()) {
            LoadingEpoxyModel().id("loading_${UUID.randomUUID()}").addTo(this)
            return
        }

        data.forEach { uiProduct ->
            ProductEpoxyModel(uiProduct!!).id(uiProduct.product.id).addTo(this)
        }

    }

}