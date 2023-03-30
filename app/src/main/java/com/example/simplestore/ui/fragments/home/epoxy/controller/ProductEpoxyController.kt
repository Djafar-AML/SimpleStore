package com.example.simplestore.ui.fragments.home.epoxy.controller

import android.view.View
import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.TypedEpoxyController
import com.example.simplestore.model.domain.Filter
import com.example.simplestore.model.ui.ProductsListFragmentUi
import com.example.simplestore.model.ui.UiFilter
import com.example.simplestore.ui.fragments.home.epoxy.model.LoadingEpoxyModel
import com.example.simplestore.ui.fragments.home.epoxy.model.ProductEpoxyModel
import com.example.simplestore.ui.fragments.home.epoxy.model.UiProductFilterEpoxyModel
import java.util.*

class ProductEpoxyController(
    private val onFavoriteIconClick: (Int) -> Unit,
    private val onUiProductClick: (Int) -> Unit,
    private val onFilterSelect: (Filter) -> Unit,
) : TypedEpoxyController<ProductsListFragmentUi>() {

    override fun buildModels(data: ProductsListFragmentUi?) {

        if (data == null) {
            LoadingEpoxyModel().id("loading_${UUID.randomUUID()}").addTo(this)
            return
        }

        val uiFilteredModels = uiFilterEpoxyModels(data.filters)

        CarouselModel_().models(uiFilteredModels).id("filters").addTo(this)

        data.products.forEach { uiProduct ->
            ProductEpoxyModel(
                uiProduct,
                onFavoriteIconClick,
                onUiProductClick
            ).id(uiProduct.product.id).addTo(this)
        }

    }

    private fun uiFilterEpoxyModels(uiFilters: Set<UiFilter>): List<EpoxyModel<View>> =
        uiFilters.map { uiFilter ->
            UiProductFilterEpoxyModel(
                uiFilter = uiFilter,
                onFilterSelect
            ).id(uiFilter.filter.value)
        }

}