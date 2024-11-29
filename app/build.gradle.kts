plugins {
      id("com.android.application")
      id("org.jetbrains.kotlin.android")
      id("org.jetbrains.kotlin.plugin.serialization")
      id("com.google.devtools.ksp")
      id("org.jetbrains.kotlin.plugin.compose") version "2.0.20"
}

android {
      namespace = "com.philomath.littlelemon"
      compileSdk = 35

      defaultConfig {
            applicationId = "com.philomath.littlelemon"
            minSdk = 21
            //noinspection EditedTargetSdkVersion
            targetSdk = 35
            versionCode = 1
            versionName = "1.0"

            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            vectorDrawables {
                  useSupportLibrary = true
            }
      }

      buildTypes {
            release {
                  isMinifyEnabled = false
                  proguardFiles(
                        getDefaultProguardFile("proguard-android-optimize.txt"),
                        "proguard-rules.pro"
                  )
            }
      }
      compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
      }
      kotlinOptions {
            jvmTarget = "1.8"
      }
      buildFeatures {
            compose = true
      }
      composeOptions {
            kotlinCompilerExtensionVersion = "1.4.3"
      }
      packaging {
            resources {
                  excludes += "/META-INF/{AL2.0,LGPL2.1}"
            }
      }
}

composeCompiler {
//      reportsDestination = layout.buildDirectory.dir("compose_compiler")
//      stabilityConfigurationFile = rootProject.layout.projectDirectory.file("stability_config.conf")
}

dependencies {
      implementation("androidx.core:core-ktx:1.15.0")
      implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7")
      implementation("androidx.activity:activity-compose:1.9.3")
      implementation(platform("androidx.compose:compose-bom:2024.11.00"))
      implementation("androidx.compose.ui:ui")
      implementation("androidx.compose.ui:ui-graphics")
      implementation("androidx.compose.ui:ui-tooling-preview")
      implementation("androidx.compose.material3:material3")

      /// test plugins
      testImplementation("junit:junit:4.13.2")
      androidTestImplementation("androidx.test.ext:junit:1.2.1")
      androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
      androidTestImplementation(platform("androidx.compose:compose-bom:2024.11.00"))
      androidTestImplementation("androidx.compose.ui:ui-test-junit4")
      debugImplementation("androidx.compose.ui:ui-tooling")
      debugImplementation("androidx.compose.ui:ui-test-manifest")
      implementation("androidx.navigation:navigation-compose:2.8.4")

      // coroutines
      implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

      // ktor implementation
      implementation("io.ktor:ktor-client-android:2.1.3")
      implementation("io.ktor:ktor-client-content-negotiation:2.1.3")
      implementation("io.ktor:ktor-serialization-kotlinx-json:2.1.3")

      /// room implementation
      implementation("androidx.room:room-runtime:2.6.1")
      ksp("androidx.room:room-compiler:2.6.1")
      implementation("androidx.room:room-ktx:2.6.1")  // Ensure this version is the same as your room-runtime version

      // Add the Compose Compiler dependency
      implementation("androidx.compose.compiler:compiler:1.5.15")

      // glide
      implementation("com.github.bumptech.glide:compose:1.0.0-alpha.1")
}
