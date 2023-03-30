package com.example.simplestore.ui.fragments.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.simplestore.databinding.FragmentCartBinding
import com.example.simplestore.ui.fragments.base.BaseFragment

class CartFragment : BaseFragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding by lazy { _binding!! }

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

        // todo

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}