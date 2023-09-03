import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class Koin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            dependencies {
                "implementation"(Dependencies.Koin.core)
                "implementation"(Dependencies.Koin.android)
                "implementation"(Dependencies.Koin.androidCompose)
            }
        }
    }
}
