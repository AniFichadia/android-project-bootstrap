import Versions.desugarJdkVersion

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
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

        testOptions {
            execution = "ANDROIDX_TEST_ORCHESTRATOR"
        }
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true

        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    kapt {
        generateStubs = true
    }

    signingConfigs {
//        TODO: setup signing configs as required
//        getByName("debug") {
//            storeFile = file("${rootProject.projectDir}/todo_debug.jks")
//            keyAlias = "todo"
//            keyPassword = "todo"
//            storePassword = "todo"
//        }
    }

    buildTypes {
        getByName("debug") {
//            isDebuggable = true
            isMinifyEnabled = false

//            TODO: enable
//            signingConfig = signingConfigs.getByName("debug")
        }

        getByName("release") {
//            isDebuggable = false
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")

//            TODO: setup a separate release signing config
//            signingConfig = signingConfigs.getByName("debug")
        }
    }

    buildFeatures {
        viewBinding = true
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
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:$desugarJdkVersion")

    implementation(project(":bootstrap_sdk_networking"))

    Deps.kotlin(this)
    Deps.logging(this)
    Deps.dependencyInjection(this)
    Deps.androidArchitecture(this)
    Deps.androidUi(this)
    Deps.networkingStack(this)

    Deps.unitTest(this)

    Deps.androidUiTest(this)
}
