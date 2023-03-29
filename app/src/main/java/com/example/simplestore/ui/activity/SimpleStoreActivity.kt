package com.example.simplestore.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.simplestore.R
import com.example.simplestore.databinding.ActivitySimpleStoreBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SimpleStoreActivity : AppCompatActivity() {

    private val binding by lazy { ActivitySimpleStoreBinding.inflate(layoutInflater) }

    val navController by lazy { navController() }

    private val appBarConfiguration by lazy { appBarConfigurationWithTopLevelDest() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupActionBarWithNavController(navController, appBarConfiguration)
        setupBottomNavigation()
    }

    private fun navController(): NavController {
        val navHostFragment =
            supportFragmentManager.findFragmentById(binding.navHostFragmentContainer.id) as NavHostFragment
        return navHostFragment.navController
    }

    private fun appBarConfigurationWithTopLevelDest() = AppBarConfiguration(
        topLevelDestinationIds = setOf(
            R.id.homeFragment,
            R.id.profileFragment
        )
    )

    private fun setupBottomNavigation() {
        val bottomNavigation = binding.bottomNavigationView
        setupWithNavController(bottomNavigation, navController)
    }

}