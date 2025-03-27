plugins {
    alias(libs.plugins.demo.android.library)
    alias(libs.plugins.demo.android.library.compose)
    alias(libs.plugins.demo.lint)
    alias(libs.plugins.junit)
    id(libs.plugins.kotlinx.serialization.get().pluginId)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.app.demo.ui"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.paging.compose.android)
    implementation(libs.androidx.navigation)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.coil.compose)
    implementation(libs.coil.svg)
    testImplementation(libs.junit)
    testRuntimeOnly(libs.junit.engine)
    testImplementation(libs.junit.params)
    testImplementation(libs.truth)
    testImplementation(libs.mockk)
    testImplementation(libs.coroutines.testing)
    testImplementation(libs.turbine)
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.test.manifest)
}
