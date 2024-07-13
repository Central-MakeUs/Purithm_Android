package com.cmc.purithm.convention

import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension

/**
 * 순수 Java/Kotlin 모듈에 사용하기 위함
 *
 * @author Yu Seung Woo
 * @since 2024-07-05
 * */
class JavaLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply{
                apply("java-library")
                apply("org.jetbrains.kotlin.jvm")
            }

            extensions.configure<JavaPluginExtension> {
                sourceCompatibility = JavaVersion.VERSION_17
                targetCompatibility = JavaVersion.VERSION_17
            }

            extensions.configure<KotlinProjectExtension> {
                jvmToolchain(17)
            }
        }
    }
}