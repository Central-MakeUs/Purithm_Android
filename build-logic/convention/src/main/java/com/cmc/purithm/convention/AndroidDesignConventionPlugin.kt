package com.cmc.purithm.convention

import com.android.build.gradle.LibraryExtension
import com.cmc.purithm.convention.base.configureCommonAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

/**
 * Design 모듈에 적용하기 위함
 *
 * @author Yu Seung Woo
 * @since 2024-07-05
 * */
class AndroidDesignConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target){
            pluginManager.apply {
                apply("purithm.library")
                apply("org.jetbrains.kotlin.android")
                apply("kotlin-parcelize")
            }

            extensions.configure<LibraryExtension> {
                configureCommonAndroid(this)
            }

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            dependencies {
                // Android Common
                add("implementation", libs.findLibrary("androidx-core").get())
                add("implementation", libs.findLibrary("androidx-appcompat").get())
                add("implementation", libs.findLibrary("android-material").get())

                // Android Ui 관련
                add("implementation", libs.findLibrary("androidx-constraintlayout").get())
                add("implementation", libs.findLibrary("facebook-shimmer").get())

                // Glide
                add("implementation", libs.findLibrary("glide-glide").get())
                add("implementation", libs.findLibrary("glide-compiler").get())

                // Coroutine Scope
                add("implementation", libs.findBundle("lifecycle").get())
                add("implementation", libs.findBundle("kotlinx-coroutine").get())
            }
        }
    }
}