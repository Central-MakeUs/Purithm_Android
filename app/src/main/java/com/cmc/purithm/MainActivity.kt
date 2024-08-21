package com.cmc.purithm


import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.os.bundleOf
import androidx.core.view.WindowCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.cmc.purithm.common.base.NavigationAction
import com.cmc.purithm.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavigationAction,
    NavController.OnDestinationChangedListener {

    private var binding: ActivityMainBinding? = null

    private val navHostFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.fcv_main) as NavHostFragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        binding?.bnvMain?.setupWithNavController(navHostFragment.navController)
    }


    private fun setTransparentStatusBar() {
        window.apply {
            setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
            navigationBarColor = Color.TRANSPARENT
            statusBarColor = Color.TRANSPARENT
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

    override fun navigateFilterItem(filterId: Long, popUpTo: Boolean) {
        with(navHostFragment.navController) {
            deepLinkNavigate("purithm://filter/$filterId", popUpToInclusive = popUpTo)
        }
    }

    override fun navigateReviewWrite(navigateType : Int, filterName: String, filterId: Long, thumbnail: String) {
        with(navHostFragment.navController) {
            navigate(
                R.id.navigate_review,
                bundleOf(
                    "navigateType" to navigateType,
                    "filterName" to filterName,
                    "filterId" to filterId,
                    "thumbnail" to thumbnail
                )
            )
        }
    }

    override fun navigateArtistFilter(artistId: Long) {
        with(navHostFragment.navController) {
            deepLinkNavigate("purithm://artist/filter/$artistId")
        }
    }

    override fun navigateFilterValue(filterId: Long) {
        with(navHostFragment.navController) {
            deepLinkNavigate("purithm://filter/value/$filterId")
        }
    }

    /**
     * @param type 이동되야할 fragment, 1 - filter review, 2 - my page
     * */
    override fun popBackStackAfterWriteReview(type: Int) {
        with(navHostFragment.navController) {
            if(type == 1){
                popBackStack(com.cmc.purithm.feature.filter.R.id.filterReviewFragment, false)
            } else {
                popBackStack(com.cmc.purithm.feature.filter.R.id.filterReviewFragment, false)
            }
        }
    }

    override fun navigateMyReviewHistory(
        filterId: Long,
        reviewId: Long,
        thumbnail: String,
        filterName: String
    ) {
        with(navHostFragment.navController) {
            val encodingUrl = URLEncoder.encode(thumbnail, StandardCharsets.UTF_8.toString())
            deepLinkNavigate("purithm://review/history/$filterId/$reviewId/$encodingUrl/$filterName")
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
            com.cmc.purithm.feature.filter.R.id.filterFragment,
            com.cmc.purithm.feature.review.R.id.reviewWriteFragment,
            com.cmc.purithm.feature.review.R.id.reviewHistoryFragment,
            com.cmc.purithm.feature.mypage.R.id.settingFragment,
            com.cmc.purithm.feature.mypage.R.id.myAccountFragment,
            com.cmc.purithm.feature.artist.R.id.artistFilterFragment -> {
                setBottomNavVisibility(false)
            }

            com.cmc.purithm.feature.feed.R.id.feedFragment -> {
                setBottomNavVisibility(true)
            }
            com.cmc.purithm.feature.home.R.id.homeFragment -> {
                setBottomNavVisibility(true)
            }
            com.cmc.purithm.feature.artist.R.id.artistFragment -> {
                setBottomNavVisibility(true)
            }
            com.cmc.purithm.feature.mypage.R.id.profileFragment -> {
                setBottomNavVisibility(true)
            }
        }
    }

    private fun setBottomNavVisibility(isVisible: Boolean) {
        binding?.bnvMain?.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    private fun NavController.deepLinkNavigate(
        deepLinkUrl: String,
        destinationId: Int = 0,
        popUpToInclusive: Boolean = false
    ) {
        val builder = NavOptions.Builder()
        if (popUpToInclusive) {
            builder.setPopUpTo(destinationId, true)
        }
        navigate(
            NavDeepLinkRequest.Builder
                .fromUri(deepLinkUrl.toUri())
                .build(),
            builder.build()
        )
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}