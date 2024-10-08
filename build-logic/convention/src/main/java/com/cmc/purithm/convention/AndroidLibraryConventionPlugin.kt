package com.cmc.purithm.convention

import com.android.build.gradle.LibraryExtension
import com.cmc.purithm.convention.base.configureCommonAndroid
import com.cmc.purithm.convention.base.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

/**
 * Android를 사용하는 Library(common...)에 사용하기 위함
 * */
class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target){
            pluginManager.apply {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                configureCommonAndroid(this)
                defaultConfig.targetSdk = 34
            }
        }
    }
}