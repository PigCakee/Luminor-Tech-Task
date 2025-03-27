pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
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

rootProject.name = "Demo"
include(":app")
include(":common:di")
include(":common:ui")
include(":common:utility")
include(":common:navigation:shared")
include(":common:navigation:impl")
include(":common:navigation:wiring")
include(":feature:auth:shared")
include(":feature:auth:impl")
include(":feature:auth:wiring")
include(":feature:auth:ui")
include(":common:storage:shared")
include(":common:storage:impl")
include(":common:storage:wiring")
