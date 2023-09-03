import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * Configure Compose-specific options
 */
internal fun Project.configureCompose(
    commonExtension: CommonExtension<*, *, *, *>,
) {
    commonExtension.apply {
        buildFeatures {
            compose = true
        }

        composeOptions {
            kotlinCompilerExtensionVersion = Dependencies.Compose.compilerVersion
        }

        kotlinOptions {
            freeCompilerArgs += listOf(
                "-Xopt-in=androidx.compose.material.ExperimentalMaterialApi",
                "-Xopt-in=androidx.compose.animation.ExperimentalAnimationApi",
            )
        }

        dependencies {
            "implementation"(Dependencies.Compose.activity)
            "implementation"(platform(Dependencies.Compose.bom))
            "implementation"(Dependencies.Compose.ui)
            "implementation"(Dependencies.Compose.uiGraphics)
            "implementation"(Dependencies.Compose.uiToolingPreview)
            "implementation"(Dependencies.Compose.material)
            "implementation"(Dependencies.Accompanist.navigationAnimation)
            "debugImplementation"(Dependencies.Compose.uiTooling)
            "debugImplementation"(Dependencies.Compose.uiTestManifest)
        }
    }
}
