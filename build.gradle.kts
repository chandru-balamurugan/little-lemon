// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
      repositories {
            google()
            mavenCentral()
      }
      dependencies {
//            classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$1.9.0")
//            classpath("com.android.tools.build:gradle:7.5.0")
//            classpath("com.google.devtools.ksp:com.google.devtools.ksp.gradle.plugin:1.9.0")
      }

}

plugins {
      id("com.android.application") version "8.7.2" apply false
      id("com.android.library") version "8.7.2" apply false
      id("org.jetbrains.kotlin.android") version "2.0.20" apply false
      id("org.jetbrains.kotlin.plugin.serialization") version "1.8.10" apply false
      id("com.google.devtools.ksp") version "2.0.21-1.0.27" apply false
//      alias(libs.plugins.compose.compiler) apply false
}
