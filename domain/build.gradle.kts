plugins {
    id("purithm.java.library")
    id("kotlin-kapt")
}

dependencies {
    implementation(libs.kotlin.coroutine.core)
    implementation(libs.kotlin.stdlib)
    implementation(libs.javax.inject)

    implementation(libs.hilt.core)
    kapt(libs.hilt.compiler)
}