package com.example.presence  // <-- change if your package is different

import android.app.Application
import android.content.pm.PackageManager
import android.util.Log
import com.google.android.libraries.places.api.Places

class PresenceApp : Application() {
    override fun onCreate() {
        super.onCreate()

        // Read the Maps/Places API key from AndroidManifest meta-data
        val ai = packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA)
        val key = ai.metaData.getString("com.google.android.geo.API_KEY") ?: ""

        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, key)
            Log.d("PresenceApp", "Google Places initialized")
        }
    }
}
