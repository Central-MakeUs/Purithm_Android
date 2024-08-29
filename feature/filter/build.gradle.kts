plugins {
    id("purithm.feature")
}

android {
    namespace = "com.cmc.purithm.feature.filter"

    defaultConfig {
        renderscriptTargetApi = 21
        renderscriptSupportModeEnabled = true
    }
}

dependencies {
    implementation("jp.wasabeef:blurry:4.0.1")
}