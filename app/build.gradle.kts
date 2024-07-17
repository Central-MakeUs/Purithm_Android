import org.jetbrains.kotlin.scripting.compiler.plugin.impl.failure
import java.time.LocalDate
import java.time.LocalDateTime

plugins {
    id("purithm.application")
}

android {
    namespace = "com.cmc.purithm"

    applicationVariants.all {
        outputs.forEach { output ->
            if (output is com.android.build.gradle.internal.api.BaseVariantOutputImpl) {

                val localDateTime = LocalDate.now()
                output.outputFileName =
                    "purithm_v${versionName}(${localDateTime})-${name}.${output.outputFile.extension}"
            }
        }
    }
}