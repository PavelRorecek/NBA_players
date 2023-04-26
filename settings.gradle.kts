pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "NBA Players"

include(":app")
include(":core-design")
include(":core-network")
include(":core-player")
include(":core-player-test-fixtures")
include(":core-test")
include(":feature-player-detail")
include(":feature-player-list")
include(":feature-team-detail")
