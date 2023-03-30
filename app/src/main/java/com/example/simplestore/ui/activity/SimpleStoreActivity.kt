package com.example.simplestore.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.airbnb.epoxy.Carousel
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
        preventCarouselSnapping()
    }

    private fun navController(): NavController {
        val navHostFragment =
            supportFragmentManager.findFragmentById(binding.navHostFragmentContainer.id) as NavHostFragment
        return navHostFragment.navController
    }

    private fun appBarConfigurationWithTopLevelDest() = AppBarConfiguration(
        topLevelDestinationIds = setOf(
            R.id.productsListFragment,
            R.id.profileFragment,
            R.id.cartFragment
        )
    )

    private fun setupBottomNavigation() {
        val bottomNavigation = binding.bottomNavigationView
        setupWithNavController(bottomNavigation, navController)
    }

    private fun preventCarouselSnapping() {
        Carousel.setDefaultGlobalSnapHelperFactory(null)
    }

}