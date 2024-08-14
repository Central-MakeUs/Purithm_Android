package com.cmc.purithm


import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.os.bundleOf
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.cmc.purithm.common.base.NavigationAction
import com.cmc.purithm.databinding.ActivityMainBinding
import com.cmc.purithm.design.util.Util
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

        if (savedInstanceState == null) {
            initBottomNavigation()
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        initBottomNavigation()
    }

    private fun initBottomNavigation() {
        binding?.bnvMain?.addOnItemSelectedListener(navHostFragment.navController)
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

    private fun getNavigationHeight(): Int {
        val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")

        return if (resourceId > 0) resources.getDimensionPixelSize(resourceId)
        else 0
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
        with(navHostFragment.navController) {
            navigate(R.id.navigate_home)
        }
    }

    override fun navigateTermOfService() {
        with(navHostFragment.navController) {
            navigate(R.id.navigate_term_of_service)
        }
    }

    override fun navigateFilterItem(filterId: Long) {
        with(navHostFragment.navController) {
            deepLinkNavigate("purithm://filter/$filterId")
        }
    }

    override fun navigateReviewWrite(filterId: Long, thumbnail: String) {
        with(navHostFragment.navController) {
            navigate(
                R.id.navigate_review,
                bundleOf("filterId" to filterId, "thumbnail" to thumbnail)
            )
        }
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
            com.cmc.purithm.feature.term.R.id.joinCompleteFragment,
            com.cmc.purithm.feature.term.R.id.termOfServiceFragment,
            com.cmc.purithm.feature.onboarding.R.id.onBoardingFragment,
            com.cmc.purithm.feature.login.R.id.loginFragment,
            com.cmc.purithm.feature.splash.R.id.splashFragment,
            com.cmc.purithm.feature.filter.R.id.filterFragment -> {
                setBottomNavVisibility(false)
            }

            com.cmc.purithm.feature.home.R.id.homeFragment -> {
                setBottomNavVisibility(true)
            }
        }
    }

    private fun setBottomNavVisibility(isVisible: Boolean) {
        binding?.bnvMain?.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    private fun NavController.deepLinkNavigate(
        deepLinkUrl: String,
        popUpTo: Boolean = false
    ) {
        val builder = NavOptions.Builder()
        if (popUpTo) {
            builder.setPopUpTo(graph.startDestinationId, true)
        }
        navigate(
            NavDeepLinkRequest.Builder
                .fromUri(deepLinkUrl.toUri())
                .build(),
            builder.build()
        )
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        when (navHostFragment.navController.currentDestination?.id) {
            com.cmc.purithm.feature.onboarding.R.id.onBoardingFragment,
            com.cmc.purithm.feature.splash.R.id.splashFragment,
            com.cmc.purithm.feature.home.R.id.homeFragment,
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