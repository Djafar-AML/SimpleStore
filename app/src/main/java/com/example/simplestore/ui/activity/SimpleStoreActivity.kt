package com.example.simplestore.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.simplestore.databinding.ActivitySimpleStoreBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SimpleStoreActivity : AppCompatActivity() {

    private val binding by lazy { ActivitySimpleStoreBinding.inflate(layoutInflater) }

    val navController by lazy { navController() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    private fun navController(): NavController {
        val navHostFragment =
            supportFragmentManager.findFragmentById(binding.navHostFragmentContainer.id) as NavHostFragment
        return navHostFragment.navController
    }

}