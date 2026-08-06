plugins {
    id("com.android.application")
}

android {
    namespace = "com.reddington.scopesighter"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.reddington.scopesighter"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.3.3"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
}