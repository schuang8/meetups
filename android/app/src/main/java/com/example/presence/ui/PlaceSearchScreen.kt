package com.example.presence.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.example.presence.data.*

@Composable
fun PlaceSearchScreen(
    modifier: Modifier = Modifier,
    onPlaceChosen: (PlaceDetails) -> Unit
) {
    val context = LocalContext.current
    val repo = remember { PlacesRepository(context) }

    var query by remember { mutableStateOf(TextFieldValue("")) }
    var results by remember { mutableStateOf<List<PlaceSuggestion>>(emptyList()) }
    var error by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()
    var searchJob by remember { mutableStateOf<Job?>(null) }

    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        OutlinedTextField(
            value = query,
            onValueChange = {
                query = it
                error = null
                searchJob?.cancel()
                searchJob = scope.launch {
                    delay(300) // debounce
                    try {
                        results = repo.autocomplete(it.text)
                    } catch (e: Exception) {
                        error = e.message
                        results = emptyList()
                    }
                }
            },
            label = { Text("Search places") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        if (error != null) {
            Text("Error: $error", color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(top = 8.dp))
        }

        Spacer(Modifier.height(12.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(results) { item ->
                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            scope.launch {
                                try {
                                    val details = repo.fetchDetails(item.placeId)
                                    onPlaceChosen(details)
                                } catch (e: Exception) {
                                    error = e.message
                                }
                            }
                        }
                ) {
                    Column(Modifier.padding(12.dp)) {
                        Text(item.primaryText, style = MaterialTheme.typography.titleMedium)
                        item.secondaryText?.let {
                            Spacer(Modifier.height(2.dp))
                            Text(it, style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
        }
    }
}
