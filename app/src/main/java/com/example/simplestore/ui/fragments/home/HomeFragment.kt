package com.example.simplestore.ui.fragments.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.example.simplestore.R
import com.example.simplestore.databinding.FragmentHomeBinding
import com.example.simplestore.extensions.loadByCoil
import com.example.simplestore.ui.fragments.base.BaseFragment
import com.example.simplestore.ui.fragments.home.vm.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    // region class leve variables
    private var _binding: FragmentHomeBinding? = null
    private val binding by lazy { _binding!! }

    private val viewMode: HomeViewModel by viewModels()

    // endregion class leve variables

    // region onCreateView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }
    //endregion onCreateView

    // region onCreateView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupClickListeners()
    }

    private fun setupClickListeners() {

        binding.apply {

            cardView.setOnClickListener {
                productDescriptionTextView.apply {
                    isVisible = !isVisible
                }
            }

            addToCartButton.setOnClickListener {
                inCartView.apply {
                    isVisible = !isVisible
                }
            }

            var isFavorite = false
            favoriteImageView.setOnClickListener {
                val imageRes = if (isFavorite) {
                    R.drawable.ic_round_favorite_border_24
                } else {
                    R.drawable.ic_round_favorite_24
                }

                favoriteImageView.setIconResource(imageRes)
                isFavorite = !isFavorite
            }
        }

    }
    // endregion onViewCreated

    private fun setupObservers() {
        viewMode.characterByIdLiveData.observe(viewLifecycleOwner) {
            Log.e(TAG, "setupObservers: $it")
        }

        binding.productImageViewLoadingProgressBar.isVisible = true
        val imageUrl = "https://fakestoreapi.com/img/71YAIFU48IL._AC_UL640_QL65_ML3_.jpg"
        binding.productImageView.loadByCoil(imageUrl) { request, result ->
            binding.productImageViewLoadingProgressBar.isGone = true
        }

    }

    // region onDestroyView
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    // endregion onDestroyView

}