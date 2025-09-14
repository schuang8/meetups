package com.example.presence

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

import com.example.presence.data.FirestoreRepository
import com.example.presence.data.PlaceDetails
import com.example.presence.data.PlaceSuggestion
import com.example.presence.data.PlacesRepository
import com.example.presence.ui.PlaceCard
import com.google.maps.android.compose.rememberUpdatedMarkerState

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface {
                    // --- Firestore (unchanged): drives your PlaceCards list
                    val feedRepo = remember { FirestoreRepository() }
                    val items by feedRepo.nearbyFlow().collectAsStateWithLifecycle(emptyList())

                    // --- Places search state
                    val context = LocalContext.current
                    val placesRepo = remember { PlacesRepository(context) }
                    var query by remember { mutableStateOf(TextFieldValue("")) }
                    var suggestions by remember { mutableStateOf<List<PlaceSuggestion>>(emptyList()) }
                    var chosen by remember { mutableStateOf<PlaceDetails?>(null) }
                    var searchError by remember { mutableStateOf<String?>(null) }
                    val scope = rememberCoroutineScope()
                    var searchJob by remember { mutableStateOf<Job?>(null) }


                    Scaffold(
                        topBar = { TopAppBar(
                            title = { Text("Friends Near You") }
                            // optional:
                            // colors = TopAppBarDefaults.topAppBarColors()
                        )}
                    ) { padding ->
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(padding)
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            // ---------- Search bar ----------
                            OutlinedTextField(
                                value = query,
                                onValueChange = {
                                    query = it
                                    searchError = null
                                    // Debounce queries so we don't hammer Places
                                    searchJob?.cancel()
                                    searchJob = scope.launch {
                                        delay(300)
                                        try {
                                            suggestions = if (it.text.isBlank()) {
                                                emptyList()
                                            } else {
                                                placesRepo.autocomplete(it.text)
                                            }
                                        } catch (e: Exception) {
                                            suggestions = emptyList()
                                            searchError = e.message
                                        }
                                    }
                                },
                                label = { Text("Search places") },
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth()
                            )

                            if (searchError != null) {
                                Text(
                                    "Error: $searchError",
                                    color = MaterialTheme.colorScheme.error,
                                    modifier = Modifier.padding(top = 8.dp)
                                )
                            }

                            // ---------- Suggestions list (appears only when there are matches) ----------
                            if (suggestions.isNotEmpty()) {
                                Spacer(Modifier.height(8.dp))
                                LazyColumn(
                                    verticalArrangement = Arrangement.spacedBy(8.dp),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .heightIn(max = 280.dp) // keeps feed visible below
                                ) {
                                    items(suggestions) { s ->
                                        ElevatedCard(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .clickable {
                                                    scope.launch {
                                                        try {
                                                            val details = placesRepo.fetchDetails(s.placeId)
                                                            chosen = details
                                                            // Optional: clear suggestions after selection
                                                            suggestions = emptyList()
                                                            // TODO: you can write 'details' to Firestore (venues) or navigate
                                                        } catch (e: Exception) {
                                                            searchError = e.message
                                                        }
                                                    }
                                                }
                                        ) {
                                            Column(Modifier.padding(12.dp)) {
                                                Text(s.primaryText, style = MaterialTheme.typography.titleMedium)
                                                s.secondaryText?.let {
                                                    Spacer(Modifier.height(2.dp))
                                                    Text(it, style = MaterialTheme.typography.bodySmall)
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            // ---------- (Optional) tiny confirmation for last chosen place ----------
                            // ---------- (Optional) tiny confirmation + map preview for last chosen place ----------
                            chosen?.let { p ->
                                Spacer(Modifier.height(8.dp))
                                Text(
                                    "Selected: ${p.name}" + (p.address?.let { " — $it" } ?: ""),
                                    style = MaterialTheme.typography.bodyMedium
                                )

                                val latLng = LatLng(p.lat, p.lng)

                                // Camera state is already remembered — this is fine
                                val cameraPositionState = rememberCameraPositionState {
                                    position = CameraPosition.fromLatLngZoom(latLng, 15f)
                                }

                                // ✅ Remember the marker state instead of constructing it inline
                                val markerState = rememberUpdatedMarkerState(position = latLng)
                                // Keep marker in sync if selection changes
                                LaunchedEffect(latLng) { markerState.position = latLng }

                                Spacer(Modifier.height(8.dp))
                                GoogleMap(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(220.dp),
                                    cameraPositionState = cameraPositionState
                                ) {
                                    Marker(
                                        state = markerState,
                                        title = p.name
                                    )
                                }
                            }

                            // ---------- Original feed of PlaceCards (kept as-is) ----------
                            Spacer(Modifier.height(12.dp))
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxSize(),
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                items(items) { place ->
                                    PlaceCard(place = place, onOpen = {})
                                }
                                item {
                                    Text(
                                        "Live data updates automatically when Firestore changes.",
                                        style = MaterialTheme.typography.bodySmall,
                                        fontWeight = FontWeight.Light,
                                        modifier = Modifier.padding(vertical = 24.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
