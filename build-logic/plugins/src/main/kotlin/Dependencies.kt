object Dependencies {

    object Accompanist {
        const val navigationAnimation =
            "com.google.accompanist:accompanist-navigation-animation:0.33.1-alpha"
    }

    object AndroidSdk {
        const val compile = 34
        const val min = 24
    }

    object Compose {
        const val compilerVersion = "1.5.3"
        const val activity = "androidx.activity:activity-compose:1.8.0-alpha07"
        const val bom = "androidx.compose:compose-bom:2023.08.00"
        const val ui = "androidx.compose.ui:ui"
        const val uiGraphics = "androidx.compose.ui:ui-graphics"
        const val uiToolingPreview = "androidx.compose.ui:ui-tooling-preview"
        const val material = "androidx.compose.material:material:1.6.0-alpha04"
        const val uiTooling = "androidx.compose.ui:ui-tooling"
        const val uiTestManifest = "androidx.compose.ui:ui-test-manifest"
    }

    object Coroutines {
        const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3"
    }

    const val junit = "junit:junit:4.13.2"

    object Kotest {
        const val core = "io.kotest:kotest-assertions-core:5.7.0"
    }

    object Koin {
        private const val version = "3.4.3"
        const val core = "io.insert-koin:koin-core:$version"
        const val android = "io.insert-koin:koin-android:$version"
        const val androidCompose = "io.insert-koin:koin-androidx-compose:3.4.6"
    }

    const val mockk = "io.mockk:mockk:1.13.7"

    object OkHttp {
        const val interceptor = "com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.11"
    }

    object Retrofit {
        const val core = "com.squareup.retrofit2:retrofit:2.9.0"
        const val gson = "com.squareup.retrofit2:converter-gson:2.9.0"
    }

    const val turbine = "app.cash.turbine:turbine:1.0.0"
}
