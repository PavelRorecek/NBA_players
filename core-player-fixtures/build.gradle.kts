plugins {
    id("plugin.core")
}

android {
    namespace = "com.pavelrorecek.core.player.fixtures"
}

dependencies {
    implementation(project(":core-player"))
}
