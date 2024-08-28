package com.cmc.purithm.common.view

import android.content.Context
import android.util.AttributeSet
import androidx.navigation.NavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView

class PurithmBottomNavigation: BottomNavigationView {
    constructor(context: Context): super(context, null)
    constructor(context: Context, attrs: AttributeSet?): super(context, attrs, com.google.android.material.R.attr.bottomNavigationStyle)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int): super(context, attrs, defStyleAttr, com.google.android.material.R.style.Widget_Design_BottomNavigationView)

    fun addOnItemSelectedListener(navController: NavController) {
        setOnItemSelectedListener { item ->
            return@setOnItemSelectedListener NavigationUI
                .onNavDestinationSelected(item, navController)
        }

        setOnItemReselectedListener { item ->
            navController.navigate(item.itemId) // 내비게이션의 초기 상태(startDestination)로 돌아갑니다.
        }
    }
}