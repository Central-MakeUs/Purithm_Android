package com.cmc.purithm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import com.cmc.purithm.common.ui.base.NavigationAction
import com.cmc.purithm.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavigationAction {

    private var binding : ActivityMainBinding? = null

    private val navHostFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.fcv_main) as NavHostFragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding?.lifecycleOwner = this
    }

    override fun navigateSplashToLogin() {
        with(navHostFragment.navController) {
            navigate(R.id.action_splash_to_login)
        }
    }

    override fun navigateLoginToMain() {

    }

    override fun navigateLoginToJoin() {

    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}