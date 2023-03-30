package com.example.simplestore.ui.fragments.productslistfragment.epoxy.controller

import android.view.View
import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.TypedEpoxyController
import com.example.simplestore.model.domain.Filter
import com.example.simplestore.model.ui.ProductsListFragmentUi
import com.example.simplestore.model.ui.UiFilter
import com.example.simplestore.ui.fragments.productslistfragment.epoxy.model.LoadingEpoxyModel
import com.example.simplestore.ui.fragments.productslistfragment.epoxy.model.ProductEpoxyModel
import com.example.simplestore.ui.fragments.productslistfragment.epoxy.model.UiProductFilterEpoxyModel

class ProductEpoxyController(
    private val onFavoriteIconClick: (Int) -> Unit,
    private val onUiProductClick: (Int) -> Unit,
    private val onFilterSelect: (Filter) -> Unit,
) : TypedEpoxyController<ProductsListFragmentUi>() {

    override fun buildModels(data: ProductsListFragmentUi?) {

        when (data) {

            is ProductsListFragmentUi.Loading -> {
                LoadingEpoxyModel().id("loading").addTo(this)
                return
            }

            is ProductsListFragmentUi.Success -> {

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

            else -> {
                throw RuntimeException("data is ${data?.javaClass?.simpleName}")
            }
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