package com.example.presence.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.presence.data.PlaceAggregate

@Composable
fun PlaceCard(place: PlaceAggregate, onOpen: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onOpen() }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(place.name, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(6.dp))
            Text("${place.countHere} here • ${place.countInterested} interested")
            if (place.sampleEtaMins != null) {
                Spacer(Modifier.height(4.dp))
                Text("ETAs: ~${place.sampleEtaMins} mins")
            }
        }
    }
}
