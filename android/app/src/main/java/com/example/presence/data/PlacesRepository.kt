package com.example.presence.data

import android.content.Context
import com.google.android.gms.tasks.Task
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

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

class PlacesRepository(context: Context) {
    private val client: PlacesClient = Places.createClient(context.applicationContext)

    suspend fun autocomplete(query: String): List<PlaceSuggestion> {
        if (query.isBlank()) return emptyList()
        val token = AutocompleteSessionToken.newInstance()
        val request = FindAutocompletePredictionsRequest.builder()
            .setSessionToken(token)
            .setQuery(query)
            .build()

        val resp = client.await { findAutocompletePredictions(request) }
        return resp.autocompletePredictions.map {
            PlaceSuggestion(
                placeId = it.placeId,
                primaryText = it.getPrimaryText(null).toString(),
                secondaryText = it.getSecondaryText(null)?.toString()
            )
        }
    }

    suspend fun fetchDetails(placeId: String): PlaceDetails {
        val fields = listOf(
            Place.Field.ID,
            Place.Field.DISPLAY_NAME,
            Place.Field.FORMATTED_ADDRESS,
            Place.Field.LOCATION
        )
        val req = FetchPlaceRequest.builder(placeId, fields).build()
        val p = client.await { fetchPlace(req) }.place
        val loc = p.location ?: error("No location for place $placeId")
        return PlaceDetails(
            placeId = p.id!!,
            name = p.displayName ?: "Unknown",
            address = p.formattedAddress,
            lat = loc.latitude,
            lng = loc.longitude
        )
    }
}

private suspend fun <T> PlacesClient.await(block: PlacesClient.() -> Task<T>): T =
    suspendCancellableCoroutine { cont ->
        val task = this.block()
        task.addOnSuccessListener { if (cont.isActive) cont.resume(it) }
            .addOnFailureListener { if (cont.isActive) cont.resumeWithException(it) }
        // There's no direct cancellation for Task; nothing to do here.
        cont.invokeOnCancellation { /* no-op */ }
    }

