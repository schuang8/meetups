plugins {
  id("com.android.application")
  id("org.jetbrains.kotlin.android")
  // id("com.google.gms.google-services") // enable when adding Firebase
}

android {
  namespace = "com.example.presence"
  compileSdk = 34

  defaultConfig {
    applicationId = "com.example.presence"
    minSdk = 24
    targetSdk = 34
    versionCode = 1
    versionName = "0.1.0"
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

  buildFeatures {
    compose = true
  }
  composeOptions {
    kotlinCompilerExtensionVersion = "1.5.14"
  }
}

dependencies {
  // Compose BOM keeps versions in sync
  implementation(platform("androidx.compose:compose-bom:2024.06.00"))
  implementation("androidx.compose.ui:ui")
  implementation("androidx.compose.ui:ui-tooling-preview")
  implementation("androidx.compose.material3:material3")
  debugImplementation("androidx.compose.ui:ui-tooling")

  implementation("androidx.activity:activity-compose:1.9.0")
  implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
  implementation("androidx.navigation:navigation-compose:2.7.7")

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
