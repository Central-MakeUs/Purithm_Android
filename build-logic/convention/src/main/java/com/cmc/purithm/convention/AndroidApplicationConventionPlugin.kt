package com.cmc.purithm.convention

import com.android.build.api.dsl.ApplicationExtension
import com.cmc.purithm.convention.base.configureCommonAndroid
import com.cmc.purithm.convention.base.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import java.time.LocalDate
import java.util.Properties

/**
 * app 모듈에 사용되는 plugin에 대한 정보
 *
 * @author Yu Seung Woo
 * @since 2024-07-05
 * */
class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
                apply("com.google.firebase.crashlytics")
                apply("com.google.gms.google-services")
                apply("kotlin-parcelize")
                apply("androidx.navigation.safeargs.kotlin")
                apply("purithm.hilt")
                apply("kotlin-kapt")
            }
            // local.properties 정보를 가져오기 위함
            val properties = Properties()
            properties.load(project.rootProject.file("local.properties").inputStream())

            // Application 정보를 추가하기위해 'ApplicatoinExtension'을 가져옴
            extensions.configure<ApplicationExtension> {
                // app 모듈은 모든 정보가 필요함
                configureKotlinAndroid(this)
                configureCommonAndroid(this)

                defaultConfig {
                    applicationId = "com.cmc.purithm"
                    versionCode = 4
                    versionName = "1.0.0.4"

                    setProperty("archivesBaseName", "purithm_v$versionName(${LocalDate.now()})")

                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

                    targetSdk = 34
                    minSdk = 24

                    buildFeatures {
                        // gradle 8.0부터 buildConfig를 사용하기 위함
                        buildConfig = true
                    }

                    buildConfigField(
                        "String",
                        "KAKAO_NATIVE_APP_KEY",
                        properties["KAKAO_NATIVE_APP_KEY"].toString()
                    )
                }

                signingConfigs {
                    create("release") {
                        storeFile = file(properties["PURITHM_STORE_FILE"].toString())
                        storePassword = properties["PURITHM_STORE_PASSWORD"].toString()
                        keyAlias = properties["PUTITHM_KEY_ALIAS"].toString()
                        keyPassword = properties["PURITHM_KEY_PASSWORD}"].toString()
                    }
                }

                buildTypes {
                    // FIXME : 실 배포는 난독화 추가
                    release {
                        isMinifyEnabled = false
                        isDebuggable = false
                        signingConfig = signingConfigs.getByName("release")
                        proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
                    }

                    debug {
                        isMinifyEnabled = false
                        isDebuggable = true
                        proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
                    }
                }

                val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
                dependencies {
                    add("implementation", project(":domain"))
                    add("implementation", project(":data"))
                    add("implementation", project(":feature:login"))
                    add("implementation", project(":feature:splash"))
                    add("implementation", project(":feature:home"))
                    add("implementation", project(":feature:onboarding"))
                    add("implementation", project(":feature:term"))
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

                    // firebase
                    val firebaseBom = libs.findLibrary("firebase-bom").get()
                    add("implementation", platform(firebaseBom).get())
                    add("implementation", libs.findLibrary("firebase-analytics").get())
                    add("implementation", libs.findLibrary("firebase-crashlytics").get())

                    // Data Store
                    add("implementation", libs.findBundle("datastore").get())

                    // Splash Screen
                    add("implementation", libs.findLibrary("androidx-core-splashscreen").get())
                }
            }
        }
    }
}