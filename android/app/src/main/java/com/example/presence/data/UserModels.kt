package com.example.presence.data

enum class Visibility { PUBLIC, FRIENDS, PRIVATE }

data class GeoPointLite(
    val lat: Double,
    val lng: Double
)

data class User(
    val uid: String,
    val displayName: String,
    val username: String,
    val photoUrl: String? = null,
    val visibility: Visibility = Visibility.FRIENDS,
    val currentLocation: GeoPointLite,
    val updatedAtEpochMs: Long
)
