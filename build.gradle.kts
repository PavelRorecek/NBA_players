import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektCreateBaselineTask

buildscript {
    repositories {
        google()
        mavenCentral()
    }
}

plugins {
    id("com.android.application") version "8.3.0-alpha01" apply false
    id("com.android.library") version "8.3.0-alpha01" apply false
    id("org.jetbrains.kotlin.android") version "1.9.10" apply false
    id("io.gitlab.arturbosch.detekt") version "1.23.1"
    id("com.github.ben-manes.versions") version "0.47.0"
}

dependencies {
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.23.1")
}

detekt {
    buildUponDefaultConfig = true
    allRules = false
    source.from(files("$projectDir"))
    config.from(files("$projectDir/config/detekt.yml"))
    baseline = file("$projectDir/config/baseline.xml")
}

tasks.withType<Detekt> {
    exclude("**/*gradle.kts")
    exclude("**/build/**")
    exclude("resources")
    exclude(".idea")
    exclude("build-logic")
}

tasks.withType<Detekt>().configureEach {
    jvmTarget = "1.8"
}

tasks.withType<DetektCreateBaselineTask>().configureEach {
    jvmTarget = "1.8"
}
