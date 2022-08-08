// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven("https://www.jitpack.io")
    }
    dependencies {
        classpath(BuildPlugins.androidGradlePlugin)
        classpath(BuildPlugins.objectBoxGradlePlugin)
        classpath(BuildPlugins.kotlinGradlePlugin)
        classpath(BuildPlugins.jacocoGradlePlugin)
        classpath(BuildPlugins.hiltGradlePlugin)
        classpath(BuildPlugins.navigationGradlePlugin)
       // classpath(BuildPlugins.gmsGradlePlugin)
       // classpath(BuildPlugins.firebasePerfGradlePlugin)
       // classpath(BuildPlugins.firebaseCrashlyticsGradlePlugin)
        classpath(BuildPlugins.mapsPlatformGradlePlugin)
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        flatDir {
            dirs = setOf(File("libs"))
        }
        google()
        mavenCentral()
        maven("https://www.jitpack.io")
    }
}

tasks.register("cleanBuild").configure {
    delete("build")
}