plugins {
    `kotlin-dsl`
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    compileOnly(libs.android.pluginGradle)
    compileOnly(libs.kotlin.pluginGradle)
}

gradlePlugin {
    plugins {
        register("AndroidHilt") {
            id = "purithm.hilt"
            implementationClass = "com.cmc.purithm.convention.AndroidHiltConventionPlugin"
        }
        register("AndroidApplication") {
            id = "purithm.application"
            implementationClass = "com.cmc.purithm.convention.AndroidApplicationConventionPlugin"
        }
        register("AndroidData") {
            id = "purithm.data"
            implementationClass = "com.cmc.purithm.convention.AndroidDataConventionPlugin"
        }
        register("AndroidFeature") {
            id = "purithm.feature"
            implementationClass = "com.cmc.purithm.convention.AndroidFeatureConventionPlugin"
        }
        register("AndroidLibrary") {
            id = "purithm.library"
            implementationClass = "com.cmc.purithm.convention.AndroidLibraryConventionPlugin"
        }
        register("AndroidCommon"){
            id = "purithm.common"
            implementationClass = "com.cmc.purithm.convention.AndroidCommonConventionPlugin"
        }
        register("JavaLibrary") {
            id = "purithm.java.library"
            implementationClass = "com.cmc.purithm.convention.JavaLibraryConventionPlugin"
        }
    }
}