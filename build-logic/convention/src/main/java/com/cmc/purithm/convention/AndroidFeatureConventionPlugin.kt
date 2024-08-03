package com.cmc.purithm.convention

import com.android.build.gradle.LibraryExtension
import com.cmc.purithm.convention.base.configureCommonAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.konan.properties.Properties

/**
 * Feature 모듈에 적용하기 위함
 *
 * @author Yu Seung Woo
 * @since 2024-07-05
 * */
class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target){
            pluginManager.apply {
                apply("purithm.library")
                apply("org.jetbrains.kotlin.android")
                apply("kotlin-parcelize")
                apply("purithm.hilt")
                apply("androidx.navigation.safeargs.kotlin")
            }

            val properties = Properties()
            properties.load(project.rootProject.file("local.properties").inputStream())

            extensions.configure<LibraryExtension> {
                configureCommonAndroid(this)
                defaultConfig{
                    manifestPlaceholders["KAKAO_NATIVE_APP_MANIFEST_KEY"] = properties["KAKAO_NATIVE_APP_MANIFEST_KEY"].toString()
                }
            }

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            dependencies {
                add("implementation", project(":domain"))
                add("implementation", project(":common"))
                add("implementation", project(":design"))

                // Android Common
                add("implementation", libs.findLibrary("androidx-core").get())
                add("implementation", libs.findLibrary("androidx-appcompat").get())
                add("implementation", libs.findLibrary("android-material").get())

                // Android Ui 관련
                add("implementation", libs.findLibrary("androidx-constraintlayout").get())
                add("implementation", libs.findLibrary("facebook-shimmer").get())

                // Network
                add("implementation", libs.findLibrary("gson-gson").get())
                add("implementation", libs.findLibrary("squareup-gson-converter").get())
                add("implementation", libs.findLibrary("squareup-retrofit2").get())
                add("implementation", libs.findLibrary("squareup-okhttp").get())
                add("implementation", libs.findLibrary("squareup-okhttp-interceptor").get())

                // Glide
                add("implementation", libs.findLibrary("glide-glide").get())
                add("implementation", libs.findLibrary("glide-compiler").get())

                // Navigation
                add("implementation", libs.findBundle("androidx-navigation").get())
                add("implementation", libs.findLibrary("androidx-navigation-test").get())

                // Coroutine Scope
                add("implementation", libs.findBundle("lifecycle").get())
                add("implementation", libs.findBundle("kotlinx-coroutine").get())

                // Kakao SDK
                add("implementation", libs.findLibrary("kakao-sdk").get())

                // Data Store
                add("implementation", libs.findBundle("datastore").get())

                // CircleIndicator
                add("implementation", libs.findLibrary("circleindicator").get())

                // paging
                add("implementation", libs.findLibrary("androidx-paging-common").get())
            }
        }
    }
}