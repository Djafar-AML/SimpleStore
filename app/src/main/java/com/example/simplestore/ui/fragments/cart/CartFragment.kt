package com.example.simplestore.ui.fragments.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.simplestore.R
import com.example.simplestore.databinding.FragmentCartBinding
import com.example.simplestore.ui.fragments.base.BaseFragment
import com.example.simplestore.ui.fragments.cart.epoxy.controller.CartEpoxyController
import com.example.simplestore.ui.fragments.cart.vm.CartViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : BaseFragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding by lazy { _binding!! }

    private val viewModel: CartViewModel by viewModels()

    private val cartEpoxyController by lazy {
        CartEpoxyController(
            ::onFavoriteClick,
            ::onDeleteClick,
            ::onEmptyStateClick,
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        setupObservers()

    }

    private fun initViews() {
        binding.epoxyRecyclerView.setController(cartEpoxyController)
    }

    private fun setupObservers() {

        viewModel.uiCartProductListLiveData.observe(viewLifecycleOwner) { cartFragmentUi ->
            cartEpoxyController.setData(cartFragmentUi)
        }

    }

    private fun onFavoriteClick(id: Int) {
        viewModel.onFavoriteClick(id)

    }

    private fun onDeleteClick(id: Int) {
        viewModel.onDeleteClick(id)
    }

    private fun onEmptyStateClick() {
        storeActivity.navigateToTab(R.id.productsListFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}