package com.cmc.purithm.convention

import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import java.util.*

/**
 * 데이터 모듈에 적용하기 위함
 *
 * @author Yu Seung Woo
 * @since 2024-07-05
 * */
class AndroidDataConventionPlugin : Plugin<Project>{
    override fun apply(target: Project) {
        with(target){
            pluginManager.apply {
                apply("purithm.library")
                apply("org.jetbrains.kotlin.android")
                apply("kotlin-parcelize")
                apply("purithm.hilt")
            }

            val properties = Properties()
            properties.load(project.rootProject.file("local.properties").inputStream())

            extensions.configure<LibraryExtension> {
                compileSdk = 34
                defaultConfig {
                    minSdk = 26
                    buildFeatures {
                        buildConfig = true
                    }
                    buildConfigField("String", "KAKAO_REST_API_KEY", properties["KAKAO_REST_API_KEY"].toString())
                }
            }

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            dependencies {
                add("implementation", project(":domain"))

                // Network
                add("implementation", libs.findLibrary("gson-gson").get())
                add("implementation", libs.findLibrary("squareup-gson-converter").get())
                add("implementation", libs.findLibrary("squareup-retrofit2").get())
                add("implementation", libs.findLibrary("squareup-okhttp").get())
                add("implementation", libs.findLibrary("squareup-okhttp-interceptor").get())

                // Coroutine
                add("implementation", libs.findLibrary("kotlin-coroutine-core").get())

                // DataStore
                add("implementation", libs.findBundle("datastore").get())

                // paging
                add("implementation", libs.findLibrary("androidx-paging-runtime").get())
                add("implementation", libs.findLibrary("androidx-paging-common").get())
            }
        }
    }
}