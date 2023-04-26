plugins {
    id("plugin.feature")
    id("plugin.koin")
}

android {
    namespace = "com.pavelrorecek.feature.playerdetail"
}

dependencies {
    implementation(project(":core-design"))
    implementation(project(":core-network"))
    implementation(project(":core-player"))
    testImplementation(project(":core-player-test-fixtures"))
    testImplementation(project(":core-test"))
}
