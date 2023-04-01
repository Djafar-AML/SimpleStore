package com.example.simplestore.ui.fragments.productslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.simplestore.databinding.FragmentProductsListBinding
import com.example.simplestore.model.domain.Filter
import com.example.simplestore.ui.fragments.base.BaseFragment
import com.example.simplestore.ui.fragments.productslist.epoxy.controller.ProductEpoxyController
import com.example.simplestore.ui.fragments.productslist.vm.ProductsListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductsListFragment : BaseFragment() {

    // region class leve variables
    private var _binding: FragmentProductsListBinding? = null
    private val binding by lazy { _binding!! }

    private val viewModel: ProductsListViewModel by viewModels()

    private val productEpoxyController by lazy {
        ProductEpoxyController(
            ::onFavoriteIconClick,
            ::onUiProductClick,
            ::onFilterSelect,
            ::onAddToCartClick,
        )
    }

    // endregion class leve variables

    // region onCreateView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductsListBinding.inflate(layoutInflater)
        return binding.root
    }
    //endregion onCreateView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        setupObservers()
    }

    private fun initViews() {
        binding.epoxyRecyclerView.setController(productEpoxyController)
    }

    private fun setupObservers() {

        viewModel.uiProductList.observe(viewLifecycleOwner) { uiProducts ->
            productEpoxyController.setData(uiProducts)
        }

    }

    private fun onFavoriteIconClick(id: Int) {
        viewModel.updateFavoriteIcon(id)
    }

    private fun onUiProductClick(id: Int) {
        viewModel.updateIsExpanded(id)
    }

    private fun onFilterSelect(filter: Filter) {
        viewModel.updateFilterSelection(filter)
    }

    private fun onAddToCartClick(id: Int) {
        viewModel.updateOnAddToCart(id)
    }


    // region onDestroyView
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    // endregion onDestroyView

}