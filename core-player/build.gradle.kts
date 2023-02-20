plugins {
    id("plugin.core")
    id("plugin.koin")
}

android {
    namespace = "com.pavelrorecek.core.player"
}

dependencies {
    implementation(project(":core-network"))
    implementation(project(":core-test"))
}
