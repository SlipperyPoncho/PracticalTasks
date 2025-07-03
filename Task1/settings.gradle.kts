pluginManagement {
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

rootProject.name = "Task1"
include(":app")
include(":core")
include(":newsfeature")
include(":helpfeature")
include(":authfeature")
include(":profilefeature")
include(":splashfeature")
include(":searchfeature")
