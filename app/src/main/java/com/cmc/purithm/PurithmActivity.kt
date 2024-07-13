package com.cmc.purithm

import com.cmc.purithm.common.ui.base.BaseActivity
import com.cmc.purithm.common.ui.base.NavigationAction
import com.cmc.purithm.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PurithmActivity : BaseActivity<ActivityMainBinding>(), NavigationAction {
    override val layoutResourceId: Int
        get() = R.layout.activity_main

    override fun init() {
    }

    override fun navigateSplashToLogin() {

    }

    override fun navigateLoginToMain() {

    }

    override fun navigateLoginToJoin() {

    }
}