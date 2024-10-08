package com.cmc.purithm.convention

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

/**
 * 안드로이드 공통 모듈 설정
 *
 * @author Yu Seung Woo
 * @since 2024-07-05
 * */
class AndroidCommonConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("purithm.library")
                apply("androidx.navigation.safeargs.kotlin")
                apply("kotlin-kapt")
            }

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            dependencies {
                add("implementation", project(":design"))

                add("implementation", libs.findLibrary("androidx-core").get())
                add("implementation", libs.findLibrary("androidx-appcompat").get())
                add("implementation", libs.findLibrary("android-material").get())

                add("implementation", libs.findLibrary("glide-glide").get())
                add("implementation", libs.findLibrary("glide-compiler").get())

                add("implementation", libs.findLibrary("lottie").get())

                add("implementation", libs.findBundle("androidx-navigation").get())

                add("implementation", libs.findBundle("lifecycle").get())
                add("implementation", libs.findBundle("kotlinx-coroutine").get())

                add("implementation", libs.findLibrary("facebook-shimmer").get())

                add("implementation", libs.findLibrary("androidx-paging-runtime").get())
            }
        }
    }
}
