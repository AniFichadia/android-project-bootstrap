buildscript {
    repositories {
        Repos.default(this)
    }
    dependencies {
        classpath ("com.android.tools.build:gradle:${Versions.androidGradlePluginVersion}")
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlinVersion}")
    }
}

allprojects {
    repositories {
        Repos.default(this)
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
