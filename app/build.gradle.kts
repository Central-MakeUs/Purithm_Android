import org.jetbrains.kotlin.scripting.compiler.plugin.impl.failure

plugins {
    id("purithm.application")
}

android {
    namespace = "com.cmc.purithm"

    applicationVariants.all {
        outputs.forEach { output ->
            if (output is com.android.build.gradle.internal.api.BaseVariantOutputImpl) {
                output.outputFileName =
                    "purithm_v${versionName}(${versionCode})-${name}.${output.outputFile.extension}"
            }
        }
    }
}