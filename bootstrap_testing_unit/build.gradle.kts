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

        multiDexEnabled = true

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunnerArgument("clearPackageData", "true")

        consumerProguardFiles("consumer-rules.pro")

        testOptions {
            execution = "ANDROIDX_TEST_ORCHESTRATOR"
        }
    }


    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }


    buildTypes {
        getByName("debug") {
//            isDebuggable = true
            isMinifyEnabled = false
        }

        getByName("release") {
//            isDebuggable = false
            isMinifyEnabled = false
        }
    }

    testOptions {
        execution = "ANDROIDX_TEST_ORCHESTRATOR"
        unitTests.isReturnDefaultValues = true
    }

    packagingOptions {
        exclude("META-INF/AL2.0")
        exclude("META-INF/LGPL2.1")
    }
}

dependencies {
    Deps.kotlin(this)
    Deps.kotlinTesting(this)
    Deps.androidArchitecture(this)
    Deps.androidArchitectureTesting(this)

    Deps.unitTest2(this)
}
