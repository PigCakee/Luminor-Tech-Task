plugins {
    alias(libs.plugins.demo.android.library)
    alias(libs.plugins.demo.hilt)
    alias(libs.plugins.demo.lint)
}

android {
    namespace = "com.app.demo.di.common"
}
