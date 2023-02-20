plugins {
    id("plugin.core")
    id("plugin.koin")
}

android {
    namespace = "com.pavelrorecek.core.network"
}

dependencies {
    api(Dependencies.Retrofit.core)
    api(Dependencies.Retrofit.gson)
    api(Dependencies.OkHttp.interceptor)
}
