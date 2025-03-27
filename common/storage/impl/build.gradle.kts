plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.demo.hilt)
    alias(libs.plugins.demo.lint)
    alias(libs.plugins.demo.room)
    alias(libs.plugins.junit)
    id(libs.plugins.kotlinx.serialization.get().pluginId)
}

android {
    namespace = "com.app.demo.storage.impl"
    compileSdk = 35

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(project(":common:utility"))
    implementation(project(":common:storage:shared"))

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.datastore)
    implementation(libs.androidx.core.ktx)
    testImplementation(libs.junit)
    testRuntimeOnly(libs.junit.engine)
    testImplementation(libs.junit.params)
    testImplementation(libs.truth)
}