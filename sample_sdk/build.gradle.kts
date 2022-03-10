import Deps

plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    compileSdkVersion(CommonAndroidConfig.compileSdkVersion)

    defaultConfig {
        minSdkVersion(CommonAndroidConfig.minSdkVersion)
        targetSdkVersion(CommonAndroidConfig.targetSdkVersion)

//        versionCode = 1
//        versionName = "1.0"

        consumerProguardFile("consumer-rules.pro")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
        }

        getByName("release") {
            isMinifyEnabled = false
        }
    }

    packagingOptions {
        exclude("META-INF/AL2.0")
        exclude("META-INF/LGPL2.1")
    }
}

dependencies {
    implementation(project(":bootstrap_sdk_networking"))

    Deps.kotlin(this)
    Deps.logging(this)
    Deps.networkingStack(this)
    Deps.androidAnnotations(this)

    Deps.unitTest(this)
}
