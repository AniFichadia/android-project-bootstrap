import Versions.androidMaterialUiVersion
import Versions.androidXAnnotationsVersion
import Versions.androidXAppCompatVersion
import Versions.androidXArchCoreVersion
import Versions.androidXConstraintLayoutVersion
import Versions.androidXCoreVersion
import Versions.androidXEspressoVersion
import Versions.androidXFragmentVersion
import Versions.androidXJunitVersion
import Versions.androidXLifecycleVersion
import Versions.androidXRecyclerViewVersion
import Versions.androidXTestVersion
import Versions.baristaVersion
import Versions.daggerVersion
import Versions.junitVersion
import Versions.kotlinCoroutinesVersion
import Versions.kotlinVersion
import Versions.mockitoVersion
import Versions.okhttpVersion
import Versions.retrofitVersion
import Versions.timberVersion
import Versions.uiAutomatorVersion
import org.gradle.api.Action
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.ExternalModuleDependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.kotlin.dsl.accessors.runtime.addDependencyTo
import org.gradle.kotlin.dsl.kotlin

object Versions {
    const val androidGradlePluginVersion = "7.1.2"
    const val desugarJdkVersion = "1.0.10"

    const val kotlinVersion = "1.6.10"
    const val kotlinCoroutinesVersion = "1.6.0"

    const val androidXArchCoreVersion = "2.1.0"
    const val androidXLifecycleVersion = "2.3.1"
    const val androidXAnnotationsVersion = "1.2.0"

    const val androidXCoreVersion = "1.3.2"
    const val androidXAppCompatVersion = "1.2.0"
    const val androidXFragmentVersion = "1.3.3"
    const val androidMaterialUiVersion = "1.3.0"
    const val androidXConstraintLayoutVersion = "2.0.4"
    const val androidXRecyclerViewVersion = "1.2.0"

    const val timberVersion = "4.7.1"

    const val daggerVersion = "2.39.1"

    const val retrofitVersion = "2.9.0"
    const val okhttpVersion = "4.9.1"

    const val junitVersion = "4.12"
    const val mockitoVersion = "3.5.13"

    const val androidXJunitVersion = "1.1.2"
    const val androidXEspressoVersion = "3.3.0"
    const val androidXTestVersion = "1.3.0"
    const val uiAutomatorVersion = "2.2.0"
    const val baristaVersion = "3.9.0"
}


object Deps {
    fun kotlin(scope: DependencyHandler) = scope.apply {
        implementation(kotlin("stdlib-jdk8", kotlinVersion))
        implementation(kotlin("reflect", kotlinVersion))

        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinCoroutinesVersion")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-debug:$kotlinCoroutinesVersion")

        kotlinTesting(this, "test")
    }

    fun kotlinTesting(scope: DependencyHandler, baseConfiguration: String? = null) = scope.apply {
        config(baseConfiguration, "implementation", "org.jetbrains.kotlinx:kotlinx-coroutines-test:$kotlinCoroutinesVersion")
    }

    fun androidArchitecture(scope: DependencyHandler) = scope.apply {
        implementation("androidx.arch.core:core-common:$androidXArchCoreVersion")
        implementation("androidx.arch.core:core-runtime:$androidXArchCoreVersion")

        implementation("androidx.lifecycle:lifecycle-common:$androidXLifecycleVersion")
        implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
        implementation("androidx.lifecycle:lifecycle-livedata:$androidXLifecycleVersion")
        implementation("androidx.lifecycle:lifecycle-viewmodel:$androidXLifecycleVersion")

        androidArchitectureTesting(this, "test")
    }

    fun androidArchitectureTesting(scope: DependencyHandler, baseConfiguration: String? = null) = scope.apply {
        config(baseConfiguration, "implementation", "androidx.arch.core:core-testing:$androidXArchCoreVersion")
    }

    fun androidAnnotations(scope: DependencyHandler) = scope.apply {
        implementation("androidx.annotation:annotation:$androidXAnnotationsVersion")
    }

    fun androidUi(scope: DependencyHandler) = scope.apply {
        implementation("androidx.core:core:$androidXCoreVersion")
        implementation("androidx.core:core-ktx:$androidXCoreVersion")

        implementation("androidx.appcompat:appcompat:$androidXAppCompatVersion")

        implementation("androidx.fragment:fragment:$androidXFragmentVersion")
        implementation("androidx.fragment:fragment-ktx:$androidXFragmentVersion")

        implementation("com.google.android.material:material:$androidMaterialUiVersion")
        implementation("androidx.constraintlayout:constraintlayout:$androidXConstraintLayoutVersion")
        implementation("androidx.recyclerview:recyclerview:$androidXRecyclerViewVersion")
    }

    fun logging(scope: DependencyHandler) = scope.apply {
        implementation("com.jakewharton.timber:timber:$timberVersion")
    }

    fun dependencyInjection(scope: DependencyHandler) = scope.apply {
        implementation("com.google.dagger:dagger:$daggerVersion")
        implementation("com.google.dagger:dagger-android:$daggerVersion")
        implementation("com.google.dagger:dagger-android-support:$daggerVersion")
        annotationProcessor("com.google.dagger:dagger-compiler:$daggerVersion")
        kapt("com.google.dagger:dagger-compiler:$daggerVersion")
        annotationProcessor("com.google.dagger:dagger-android-processor:$daggerVersion")
        kapt("com.google.dagger:dagger-android-processor:$daggerVersion")
    }

    fun networkingStack(scope: DependencyHandler) = scope.apply {
        implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
        implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")

        implementation("com.squareup.okhttp3:okhttp:$okhttpVersion")
        implementation("com.squareup.okhttp3:logging-interceptor:$okhttpVersion")
    }

    fun unitTest(scope: DependencyHandler) = scope.apply {
        unitTest2(this, "test")
    }

    fun unitTest2(scope: DependencyHandler, baseConfiguration: String? = null) = scope.apply {
        config(baseConfiguration, "implementation", "junit:junit:$junitVersion")
        config(baseConfiguration, "implementation", "org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion")
        config(baseConfiguration, "implementation", "org.mockito:mockito-core:$mockitoVersion")
        // TODO: for integration tests in SDKs, use wiremock for stub mocking
    }

    fun androidUiTest(scope: DependencyHandler) = scope.apply {
        debugImplementation("androidx.fragment:fragment-testing:$androidXFragmentVersion") {
            exclude(
                mapOf(
                    "group" to "androidx.test",
                    "module" to "core"
                )
            )
        }

        androidTestImplementation("androidx.test:core:$androidXTestVersion")
        androidTestImplementation("androidx.test:core-ktx:$androidXTestVersion")
        androidTestImplementation("androidx.test:runner:$androidXTestVersion")
        androidTestImplementation("androidx.test:rules:$androidXTestVersion")
        androidTestImplementation("androidx.test.espresso:espresso-core:$androidXEspressoVersion")
        androidTestImplementation("androidx.test.espresso:espresso-contrib:$androidXEspressoVersion")
        androidTestImplementation("androidx.test.espresso:espresso-intents:$androidXEspressoVersion")
        androidTestImplementation("androidx.test.espresso:espresso-idling-resource:$androidXEspressoVersion")
        androidTestImplementation("androidx.test.espresso.idling:idling-concurrent:$androidXEspressoVersion")
        androidTestImplementation("androidx.test.espresso.idling:idling-net:$androidXEspressoVersion")
        androidTestImplementation("androidx.test.ext:junit:$androidXJunitVersion")
        androidTestImplementation("androidx.test.ext:junit-ktx:$androidXJunitVersion")
        androidTestUtil("androidx.test:orchestrator:$androidXTestVersion")
        androidTestImplementation("androidx.test.uiautomator:uiautomator:$uiAutomatorVersion")

        androidTestImplementation("com.schibsted.spain:barista:$baristaVersion") {
            exclude(
                mapOf(
                    "group" to "org.jetbrains.kotlin"
                )
            )
        }
    }

    fun androidUiTest2(scope: DependencyHandler) = scope.apply {
        debugImplementation("androidx.fragment:fragment-testing:$androidXFragmentVersion") {
            exclude(
                mapOf(
                    "group" to "androidx.test",
                    "module" to "core"
                )
            )
        }

        kotlinTesting(this)

        implementation("androidx.test:core:$androidXTestVersion")
        implementation("androidx.test:core-ktx:$androidXTestVersion")
        implementation("androidx.test:runner:$androidXTestVersion")
        implementation("androidx.test:rules:$androidXTestVersion")
        implementation("androidx.test.espresso:espresso-core:$androidXEspressoVersion")
        implementation("androidx.test.espresso:espresso-contrib:$androidXEspressoVersion")
        implementation("androidx.test.espresso:espresso-intents:$androidXEspressoVersion")
        implementation("androidx.test.espresso:espresso-idling-resource:$androidXEspressoVersion")
        implementation("androidx.test.espresso.idling:idling-concurrent:$androidXEspressoVersion")
        implementation("androidx.test.espresso.idling:idling-net:$androidXEspressoVersion")
        implementation("androidx.test.ext:junit:$androidXJunitVersion")
        implementation("androidx.test.ext:junit-ktx:$androidXJunitVersion")
        implementation("androidx.test.uiautomator:uiautomator:$uiAutomatorVersion")

        implementation("com.schibsted.spain:barista:$baristaVersion") {
            exclude(
                mapOf(
                    "group" to "org.jetbrains.kotlin"
                )
            )
        }
    }
}

object Repos {
    fun default(scope: RepositoryHandler) = scope.apply {
        google()
        mavenCentral()
        jcenter()
    }
}

private fun DependencyHandler.`config`(
    baseConfiguration: String?,
    configurationName: String,
    dependencyNotation: String
): Dependency? = if (baseConfiguration != null) {
    add("$baseConfiguration${configurationName[0].toUpperCase()}${configurationName.substring(1)}", dependencyNotation)
} else {
    add(configurationName, dependencyNotation)
}

// Unfortunately these extensions aren't available, so this is a backport for now
private fun DependencyHandler.`kapt`(dependencyNotation: Any): Dependency? =
    add("kapt", dependencyNotation)

private fun DependencyHandler.`annotationProcessor`(dependencyNotation: Any): Dependency? =
    add("annotationProcessor", dependencyNotation)

private fun DependencyHandler.`implementation`(dependencyNotation: Any): Dependency? =
    add("implementation", dependencyNotation)

private fun DependencyHandler.`implementation`(
    dependencyNotation: String,
    dependencyConfiguration: Action<ExternalModuleDependency>
): ExternalModuleDependency = addDependencyTo(
    this, "implementation", dependencyNotation, dependencyConfiguration
)

private fun DependencyHandler.`debugImplementation`(dependencyNotation: Any): Dependency? =
    add("debugImplementation", dependencyNotation)

private fun DependencyHandler.`testImplementation`(dependencyNotation: Any): Dependency? =
    add("testImplementation", dependencyNotation)

private fun DependencyHandler.`androidTestImplementation`(dependencyNotation: Any): Dependency? =
    add("androidTestImplementation", dependencyNotation)

private fun DependencyHandler.`androidTestImplementation`(
    dependencyNotation: String,
    dependencyConfiguration: Action<ExternalModuleDependency>
): ExternalModuleDependency = addDependencyTo(
    this, "androidTestImplementation", dependencyNotation, dependencyConfiguration
)

private fun DependencyHandler.`debugImplementation`(
    dependencyNotation: String,
    dependencyConfiguration: Action<ExternalModuleDependency>
): ExternalModuleDependency = addDependencyTo(
    this, "debugImplementation", dependencyNotation, dependencyConfiguration
)

private fun DependencyHandler.`androidTestUtil`(dependencyNotation: Any): Dependency? =
    add("androidTestUtil", dependencyNotation)
