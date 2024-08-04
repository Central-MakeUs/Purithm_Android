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