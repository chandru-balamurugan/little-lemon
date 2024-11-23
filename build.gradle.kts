// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
      repositories {
            google()
            mavenCentral()
      }
      dependencies {
            // Other dependencies...

      }
}

plugins {
      id("com.android.application") version "8.7.0" apply false
      id("org.jetbrains.kotlin.android") version "1.8.10" apply false
      id("org.jetbrains.kotlin.plugin.serialization") version "1.8.10" apply false
      id("com.google.devtools.ksp") version "2.0.21-1.0.27" apply false
}
