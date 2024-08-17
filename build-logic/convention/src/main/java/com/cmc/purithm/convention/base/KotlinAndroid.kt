package com.cmc.purithm.convention.base

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

/**
 * Android 모듈에 필요한 기본 정보
 *
 * @author Yu Seung Woo
 * @since 2024-07-05
 * */
internal fun Project.configureKotlinAndroid (
    commonExtension: CommonExtension<*,*,*,*,*>
) {
    commonExtension.apply {
        compileSdk = 34

        defaultConfig {
            minSdk = 26

            testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
            vectorDrawables.useSupportLibrary = true
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }
        kotlinOptions {
            jvmTarget = JavaVersion.VERSION_17.toString()
        }
    }
}

fun CommonExtension<*, *, *, *, *>.kotlinOptions(block: KotlinJvmOptions.() -> Unit) {
    (this as ExtensionAware).extensions.configure("kotlinOptions", block)
}