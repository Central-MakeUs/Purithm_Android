plugins {
    id("purithm.java.library")
    id("kotlin-kapt")
}

dependencies {
    implementation(libs.kotlin.coroutine.core)
    implementation(libs.kotlin.stdlib)
    implementation(libs.javax.inject)
    // common은 android 종속성이 없음
    implementation(libs.androidx.paging.common)

    implementation(libs.hilt.core)
    kapt(libs.hilt.compiler)
}