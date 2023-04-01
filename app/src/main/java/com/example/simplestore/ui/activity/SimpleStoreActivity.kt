package com.example.simplestore.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.airbnb.epoxy.Carousel
import com.example.simplestore.R
import com.example.simplestore.databinding.ActivitySimpleStoreBinding
import com.example.simplestore.ui.activity.vm.StoreActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SimpleStoreActivity : AppCompatActivity() {

    private val binding by lazy { ActivitySimpleStoreBinding.inflate(layoutInflater) }

    private val storeActivityViewModel: StoreActivityViewModel by viewModels()

    val navController by lazy { navController() }

    private val appBarConfiguration by lazy { appBarConfigurationWithTopLevelDest() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupObservers()
        setupActionBarWithNavController(navController, appBarConfiguration)
        setupBottomNavigation()
        preventCarouselSnapping()
    }

    private fun setupObservers() {

        storeActivityViewModel.inCartProductCountLiveData.observe(this) { numberOfProductsInCart ->

            binding.bottomNavigationView.getOrCreateBadge(R.id.cartFragment).apply {
                number = numberOfProductsInCart
                isVisible = numberOfProductsInCart > 0
            }

        }
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

    fun navigateToTab(@IdRes destination: Int) {
        binding.bottomNavigationView.selectedItemId = destination
    }
}