package com.example.presence.data

data class PlaceSuggestion(
    val placeId: String,
    val primaryText: String,
    val secondaryText: String?
)

data class PlaceDetails(
    val placeId: String,
    val name: String,
    val address: String?,
    val lat: Double,
    val lng: Double
)

data class PlaceAggregate(
    val id: String,
    val name: String,
    val countHere: Int,
    val countInterested: Int,
    val sampleEtaMins: Int? = null
)
