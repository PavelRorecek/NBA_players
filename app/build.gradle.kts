plugins {
    id("plugin.app")
}

android {
    namespace = "com.pavelrorecek.nbaplayers"

    defaultConfig {
        applicationId = "com.pavelrorecek.nbaplayers"
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }

    packagingOptions {
        resources {
            excludes += "/META-INF/*"
        }
    }
}

dependencies {
    implementation(project(":core-design"))
    implementation(project(":core-network"))
    implementation(project(":core-player"))
    implementation(project(":feature-player-list"))
    implementation(project(":feature-player-detail"))
}
