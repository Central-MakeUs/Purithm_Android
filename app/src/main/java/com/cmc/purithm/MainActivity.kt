package com.cmc.purithm

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import com.cmc.purithm.common.ui.base.NavigationAction
import com.cmc.purithm.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavigationAction,
    NavController.OnDestinationChangedListener {

    private var binding: ActivityMainBinding? = null

    private val navHostFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.fcv_main) as NavHostFragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding?.lifecycleOwner = this

        navHostFragment.navController.addOnDestinationChangedListener(this)
    }

    override fun navigateLogin() {
        with(navHostFragment.navController) {
            navigate(R.id.navigate_login)
        }
    }

    override fun navigateOnBoarding() {
        with(navHostFragment.navController) {
            navigate(R.id.navigate_onboarding)
        }
    }

    override fun navigateMain() {

    }

    override fun navigateHome() {

    }

    override fun navigateTerm() {

    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        when (destination.id) {
            com.cmc.purithm.feature.splash.R.id.splashFragment -> {
                setBottomNavVisibility(false)
            }

            com.cmc.purithm.feature.login.R.id.loginFragment -> {
                setBottomNavVisibility(false)
            }
            com.cmc.purithm.feature.onboarding.R.id.onBoardingFragment -> {
                setBottomNavVisibility(false)
            }
        }
    }

    private fun setBottomNavVisibility(isVisible: Boolean) {
        binding?.bnvMain?.visibility = if (isVisible) View.VISIBLE else View.GONE
    }
}