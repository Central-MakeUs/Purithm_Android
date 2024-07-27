package com.cmc.purithm


import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import com.cmc.purithm.common.base.NavigationAction
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

        setTransparentStatusBar()
    }

    private fun setTransparentStatusBar() {
        window.apply {
            setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            WindowCompat.setDecorFitsSystemWindows(window, false)
        }
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
                setAppbarVisibility(false)
                setBottomNavVisibility(false)
            }

            com.cmc.purithm.feature.login.R.id.loginFragment -> {
                setAppbarVisibility(true)
                setBottomNavVisibility(false)
            }

            com.cmc.purithm.feature.onboarding.R.id.onBoardingFragment -> {
                setAppbarVisibility(false)
                setBottomNavVisibility(false)
            }
        }
    }

    private fun setAppbarVisibility(isVisible: Boolean) {
        binding?.viewAppbar?.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    private fun setBottomNavVisibility(isVisible: Boolean) {
        binding?.bnvMain?.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        when (navHostFragment.navController.currentDestination?.id) {
            com.cmc.purithm.feature.onboarding.R.id.onBoardingFragment,
            com.cmc.purithm.feature.splash.R.id.splashFragment,
            com.cmc.purithm.feature.login.R.id.loginFragment -> {
                finish()
            }

            else -> super.onBackPressed()
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}