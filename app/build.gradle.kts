plugins {
    alias(libs.plugins.demo.android.application)
    alias(libs.plugins.demo.android.application.compose)
    alias(libs.plugins.demo.hilt)
    alias(libs.plugins.demo.lint)
    alias(libs.plugins.junit)
    alias(libs.plugins.demo.room)
    id(libs.plugins.kotlinx.serialization.get().pluginId)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.app.demo"

    defaultConfig {
        applicationId = "com.app.demo"
        versionCode = 1
        versionName = "1"
        multiDexEnabled = true

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isMinifyEnabled = false
        }
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {

    implementation(project(":common:di"))
    implementation(project(":common:ui"))
    implementation(project(":common:storage:shared"))
    implementation(project(":common:storage:wiring"))
    implementation(project(":common:navigation:shared"))
    implementation(project(":common:navigation:wiring"))
    implementation(project(":feature:auth:shared"))
    implementation(project(":feature:auth:wiring"))

    implementation(libs.kotlinx.serialization.json)

    implementation(libs.androidx.activity.compose)

    implementation(libs.multidex)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.material3)
    implementation(libs.splash)

    testImplementation(libs.junit)
    testRuntimeOnly(libs.junit.engine)
    testImplementation(libs.junit.params)
    testImplementation(libs.truth)
    androidTestImplementation(libs.androidx.ui.test.junit4)
}
