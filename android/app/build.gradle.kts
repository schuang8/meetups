plugins {
  id("com.android.application")
  id("org.jetbrains.kotlin.android")
  // id("com.google.gms.google-services") // enable when adding Firebase
}

kotlin {
  jvmToolchain(8) // Or your desired Java version (e.g., 11, 17)
}

android {
  namespace = "com.example.presence"
  compileSdk = 36

  defaultConfig {
    applicationId = "com.example.presence"
    minSdk = 24
    targetSdk = 34
    versionCode = 1
    versionName = "0.1.0"
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  testOptions { animationsDisabled = true }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(
        getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro"
      )
    }
  }

  buildFeatures {
    compose = true
  }
  composeOptions {
    kotlinCompilerExtensionVersion = "1.5.14"
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8 // Or your desired Java version
    targetCompatibility = JavaVersion.VERSION_1_8 // Or your desired Java version
  }

//  kotlinOptions {
//    jvmTarget = "1.8" // Or your desired Java version, matching compileOptions
//  }
}

dependencies {
  // Compose BOM keeps versions in sync
  implementation(platform("androidx.compose:compose-bom:2025.09.00"))
  implementation("androidx.compose.ui:ui")
  implementation("androidx.compose.ui:ui-tooling-preview")
  implementation("androidx.compose.material3:material3")
  implementation("com.google.android.material:material:1.13.0")
  debugImplementation("androidx.compose.ui:ui-tooling")

  implementation("androidx.activity:activity-compose:1.11.0")
  implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.9.3")
  implementation("androidx.navigation:navigation-compose:2.9.4")

  // Test dependencies
  androidTestImplementation(platform("androidx.compose:compose-bom:2025.09.00"))
  androidTestImplementation("androidx.compose.ui:ui-test-junit4")
  androidTestImplementation("androidx.test.ext:junit:1.3.0")
  androidTestImplementation("androidx.test.espresso:espresso-core:3.7.0")
  debugImplementation("androidx.compose.ui:ui-test-manifest")

  // Optional: WorkManager & Room (add later)
  // implementation("androidx.work:work-runtime-ktx:2.9.0")
  // implementation("androidx.room:room-ktx:2.6.1")
  // kapt("androidx.room:room-compiler:2.6.1")

  // Optional: Firebase (uncomment when ready, plus enable google-services plugin)
  // implementation(platform("com.google.firebase:firebase-bom:33.1.2"))
  // implementation("com.google.firebase:firebase-auth-ktx")
  // implementation("com.google.firebase:firebase-firestore-ktx")
  // implementation("com.google.firebase:firebase-messaging-ktx")
}
