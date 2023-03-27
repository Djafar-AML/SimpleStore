package com.example.simplestore.ui.fragments.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.simplestore.databinding.FragmentHomeBinding
import com.example.simplestore.ui.fragments.base.BaseFragment
import com.example.simplestore.ui.fragments.home.vm.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

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
    }
    // endregion onViewCreated

    private fun setupObservers() {
        viewMode.characterByIdLiveData.observe(viewLifecycleOwner) {
            Log.e(TAG, "setupObservers: $it")
        }

    }

    // region onDestroyView
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    // endregion onDestroyView

}