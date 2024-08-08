pluginManagement {
    includeBuild("build-logic")
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
        maven { url = uri("https://devrepo.kakao.com/nexus/content/groups/public/") }
    }
}

rootProject.name = "purithm"
include(":app")
include(":feature")
include(":data")
include(":domain")
include(":common")
include(":feature:splash")
include(":feature:login")
include(":design")
include(":feature:home")
include(":feature:onboarding")
include(":feature:term")
include(":feature:filter")
include(":feature:review")
include(":feature:feed")
include(":feature:artist")
include(":feature:mypage")
