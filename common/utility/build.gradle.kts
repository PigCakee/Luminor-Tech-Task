plugins {
    alias(libs.plugins.demo.android.library)
    alias(libs.plugins.demo.hilt)
    alias(libs.plugins.demo.lint)
}

android {
    namespace = "com.app.demo.utility"
}

dependencies {
    implementation(libs.kotlinx.serialization.json)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    testImplementation(libs.junit)
    testRuntimeOnly(libs.junit.engine)
    testImplementation(libs.junit.params)
    testImplementation(libs.truth)
}
