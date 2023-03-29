package com.example.simplestore.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.simplestore.databinding.FragmentHomeBinding
import com.example.simplestore.ui.fragments.base.BaseFragment
import com.example.simplestore.ui.fragments.home.epoxy.controller.ProductEpoxyController
import com.example.simplestore.ui.fragments.home.vm.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    // region class leve variables
    private var _binding: FragmentHomeBinding? = null
    private val binding by lazy { _binding!! }

    private val viewModel: HomeViewModel by viewModels()

    private val productEpoxyController by lazy {
        ProductEpoxyController(
            ::onFavoriteIconClick,
            ::onUiProductClick
        )
    }

    // endregion class leve variables

    // region onCreateView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }
    //endregion onCreateView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        setupObservers()
        initLoadingState()
    }

    private fun initViews() {
        binding.epoxyRecyclerView.setController(productEpoxyController)
    }

    private fun setupObservers() {

        viewModel.uiProductList.observe(viewLifecycleOwner) { uiProducts ->
            productEpoxyController.setData(uiProducts)
        }

    }

    private fun initLoadingState() {
        productEpoxyController.setData(emptyList())
    }

    private fun onFavoriteIconClick(id: Int) {
        viewModel.updateFavoriteIcon(id)
    }

    private fun onUiProductClick(id: Int) {
        viewModel.updateIsExpanded(id)
    }


    // region onDestroyView
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    // endregion onDestroyView

}