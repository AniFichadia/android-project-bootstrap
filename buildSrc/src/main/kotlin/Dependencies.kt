import Deps2.DependencyType.androidTestImplementation
import Deps2.DependencyType.androidTestUtil
import Deps2.DependencyType.annotationProcessor
import Deps2.DependencyType.debugImplementation
import Deps2.DependencyType.implementation
import Deps2.DependencyType.kapt
import Deps2.DependencyType.testImplementation
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
import Versions.daggerVersion
import Versions.junitVersion
import Versions.kotlinCoroutinesVersion
import Versions.kotlinVersion
import Versions.mockitoVersion
import Versions.okhttpVersion
import Versions.retrofitVersion
import Versions.timberVersion
import Versions.uiAutomatorVersion
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.kotlin.dsl.kotlin

object Versions {
    const val androidGradlePluginVersion = "4.1.0"
    const val desugarJdkVersion = "1.0.10"

    const val kotlinVersion = "1.4.10"
    const val kotlinCoroutinesVersion = "1.3.9"

    const val androidXArchCoreVersion = "2.1.0"
    const val androidXLifecycleVersion = "2.2.0"
    const val androidXAnnotationsVersion = "1.1.0"

    const val androidXCoreVersion = "1.3.2"
    const val androidXAppCompatVersion = "1.2.0"
    const val androidXFragmentVersion = "1.2.5"
    const val androidMaterialUiVersion = "1.2.1"
    const val androidXConstraintLayoutVersion = "2.0.2"
    const val androidXRecyclerViewVersion = "1.1.0"

    const val timberVersion = "4.7.1"

    const val daggerVersion = "2.29.1"

    const val retrofitVersion = "2.9.0"
    const val okhttpVersion = "4.9.0"

    const val junitVersion = "4.12"
    const val mockitoVersion = "3.5.13"

    const val androidXJunitVersion = "1.1.1"
    const val androidXEspressoVersion = "3.3.0"
    const val androidXTestVersion = "1.3.0"
    const val uiAutomatorVersion = "2.2.0"
}

object Deps2 {

    val kotlin = mapOf(
        implementation to kotlin("stdlib-jdk8", kotlinVersion),
        implementation to kotlin("reflect", kotlinVersion),

        implementation to kotlinX("coroutines-core", kotlinCoroutinesVersion),
        implementation to kotlinX("coroutines-debug", kotlinCoroutinesVersion),

        testImplementation to kotlinX("coroutines-test", kotlinCoroutinesVersion)
    )

    val androidArchitecture = mapOf(
        implementation to "androidx.arch.core:core-common:$androidXArchCoreVersion",
        implementation to "androidx.arch.core:core-runtime:$androidXArchCoreVersion",

        implementation to "androidx.lifecycle:lifecycle-common:$androidXLifecycleVersion",
        implementation to "androidx.lifecycle:lifecycle-extensions:$androidXLifecycleVersion",
        implementation to "androidx.lifecycle:lifecycle-livedata:$androidXLifecycleVersion",
        implementation to "androidx.lifecycle:lifecycle-viewmodel:$androidXLifecycleVersion",

        testImplementation to "androidx.arch.core:core-testing:$androidXArchCoreVersion"
    )

    val androidAnnotations = mapOf(
        implementation to "androidx.annotation:annotation:$androidXAnnotationsVersion"
    )

    val androidUi = mapOf(
        implementation to "androidx.core:core:$androidXCoreVersion",
        implementation to "androidx.core:core-ktx:$androidXCoreVersion",

        implementation to "androidx.appcompat:appcompat:$androidXAppCompatVersion",

        implementation to "androidx.fragment:fragment:$androidXFragmentVersion",
        implementation to "androidx.fragment:fragment-ktx:$androidXFragmentVersion",

        implementation to "com.google.android.material:material:$androidMaterialUiVersion",
        implementation to "androidx.constraintlayout:constraintlayout:$androidXConstraintLayoutVersion",
        implementation to "androidx.recyclerview:recyclerview:$androidXRecyclerViewVersion"
    )

    val logging = mapOf(
        implementation to "com.jakewharton.timber:timber:$timberVersion"
    )

    val dependencyInjection = mapOf(
        implementation to "com.google.dagger:dagger:$daggerVersion",
        implementation to "com.google.dagger:dagger-android:$daggerVersion",
        implementation to "com.google.dagger:dagger-android-support:$daggerVersion",
        annotationProcessor to "com.google.dagger:dagger-compiler:$daggerVersion",
        kapt to "com.google.dagger:dagger-compiler:$daggerVersion",
        annotationProcessor to "com.google.dagger:dagger-android-processor:$daggerVersion",
        kapt to "com.google.dagger:dagger-android-processor:$daggerVersion"
    )

    val networkingStack = mapOf(
        implementation to "com.squareup.retrofit2:retrofit:$retrofitVersion",
        implementation to "com.squareup.retrofit2:converter-gson:$retrofitVersion",

        implementation to "com.squareup.okhttp3:okhttp:$okhttpVersion",
        implementation to "com.squareup.okhttp3:logging-interceptor:$okhttpVersion"
    )


    val unitTest = mapOf(
        testImplementation to "junit:junit:$junitVersion",
        testImplementation to "org.mockito:mockito-core:$mockitoVersion"

        // TODO: for integration tests in SDKs, use wiremock for stub mocking
    )

    val androidUiTest = mapOf(
        debugImplementation to "androidx.fragment:fragment-testing:$androidXFragmentVersion",

        androidTestImplementation to "androidx.test:core:$androidXTestVersion",
        androidTestImplementation to "androidx.test:core-ktx:$androidXTestVersion",
        androidTestImplementation to "androidx.test:runner:$androidXTestVersion",
        androidTestImplementation to "androidx.test:rules:$androidXTestVersion",
        androidTestImplementation to "androidx.test.espresso:espresso-core:$androidXEspressoVersion",
        androidTestImplementation to "androidx.test.espresso:espresso-contrib:$androidXEspressoVersion",
        androidTestImplementation to "androidx.test.espresso:espresso-idling-resource:$androidXEspressoVersion",
        androidTestImplementation to "androidx.test.espresso.idling.idling-concurrent:$androidXEspressoVersion",
        androidTestImplementation to "androidx.test.espresso.idling.idling-net:$androidXEspressoVersion",
        androidTestImplementation to "androidx.test.ext:junit:$androidXJunitVersion",
//        androidTestUtil to "androidx.test.orchestrator:$androidXTestVersion",

        androidTestImplementation to "androidx.test.uiautomator:uiautomator:$uiAutomatorVersion"
    )


    fun Map<DependencyType, String>.install(scope: DependencyHandler) {
        entries.forEach {
            val configurationName = it.key.value
            val dependencyNotation = it.value
            scope.add(configurationName, dependencyNotation)
        }
    }

    private fun kotlin(module: String, version: String? = null): String =
        "org.jetbrains.kotlin:kotlin-$module${version?.let { ":$version" } ?: ""}"

    private fun kotlinX(module: String, version: String? = null): String =
        "org.jetbrains.kotlinx:kotlinx-$module${version?.let { ":$version" } ?: ""}"

    enum class DependencyType(val value: String) {
        implementation("implementation"),
        debugImplementation("debugImplementation"),
        testImplementation("testImplementation"),
        annotationProcessor("annotationProcessor"),
        kapt("kapt"),
        androidTestImplementation("androidTestImplementation"),
        androidTestUtil("androidTestUtil")
    }
}

object Deps {
    fun kotlin(scope: DependencyHandler) = scope.apply {
        implementation(kotlin("stdlib-jdk8", kotlinVersion))
        implementation(kotlin("reflect", kotlinVersion))

        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinCoroutinesVersion")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-debug:$kotlinCoroutinesVersion")

        testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$kotlinCoroutinesVersion")
    }

    fun androidArchitecture(scope: DependencyHandler) = scope.apply {
        implementation("androidx.arch.core:core-common:$androidXArchCoreVersion")
        implementation("androidx.arch.core:core-runtime:$androidXArchCoreVersion")

        implementation("androidx.lifecycle:lifecycle-common:$androidXLifecycleVersion")
        implementation("androidx.lifecycle:lifecycle-extensions:$androidXLifecycleVersion")
        implementation("androidx.lifecycle:lifecycle-livedata:$androidXLifecycleVersion")
        implementation("androidx.lifecycle:lifecycle-viewmodel:$androidXLifecycleVersion")

        testImplementation("androidx.arch.core:core-testing:$androidXArchCoreVersion")
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
        testImplementation("junit:junit:$junitVersion")
        testImplementation("org.mockito:mockito-core:$mockitoVersion")

        // TODO: for integration tests in SDKs, use wiremock for stub mocking
    }

    fun androidUiTest(scope: DependencyHandler) = scope.apply {
        debugImplementation("androidx.fragment:fragment-testing:$androidXFragmentVersion")

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
//        androidTestUtil("androidx.test.orchestrator:$androidXTestVersion")

        androidTestImplementation("androidx.test.uiautomator:uiautomator:$uiAutomatorVersion")
    }
}

object Repos {
    fun default(scope: RepositoryHandler) = scope.apply {
        google()
        jcenter()
    }
}


// Unfortunately these extensions aren't available, so this is a backport for now
private fun DependencyHandler.`kapt`(dependencyNotation: Any): Dependency? =
    add("kapt", dependencyNotation)

private fun DependencyHandler.`annotationProcessor`(dependencyNotation: Any): Dependency? =
    add("annotationProcessor", dependencyNotation)

private fun DependencyHandler.`implementation`(dependencyNotation: Any): Dependency? =
    add("implementation", dependencyNotation)

private fun DependencyHandler.`debugImplementation`(dependencyNotation: Any): Dependency? =
    add("debugImplementation", dependencyNotation)

private fun DependencyHandler.`testImplementation`(dependencyNotation: Any): Dependency? =
    add("testImplementation", dependencyNotation)

private fun DependencyHandler.`androidTestImplementation`(dependencyNotation: Any): Dependency? =
    add("androidTestImplementation", dependencyNotation)

