package com.example.presence.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.presence.data.Meetup
import com.example.presence.data.RsvpStatus
import com.example.presence.data.User
import com.example.presence.data.mockData.MeetupsRepositoryMock
import com.example.presence.data.mockData.UsersRepositoryMock
import java.text.DateFormat
import java.util.*

@Composable
fun MeetupsListScreen(
    modifier: Modifier = Modifier,
    currentUserId: String,
    usersRepo: UsersRepositoryMock,
    meetupsRepo: MeetupsRepositoryMock,
    onOpen: (Meetup) -> Unit
) {
    val meetups by meetupsRepo.meetups.collectAsState(initial = emptyList())
    val users by usersRepo.users.collectAsState(initial = emptyList())
    val usersById = remember(users) { users.associateBy { it.uid } }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(meetups, key = { it.id }) { m ->
            MeetupCard(
                meetup = m,
                host = usersById[m.hostUid],
                onClick = { onOpen(m) }
            )
        }
    }
}

@Composable
private fun MeetupCard(
    meetup: Meetup,
    host: User?,
    onClick: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth().clickable { onClick() }
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(meetup.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(4.dp))
            Text(
                text = buildString {
                    append(formatRange(meetup.startAtEpochMs, meetup.endAtEpochMs))
                    host?.let { append("  •  Host: ${it.displayName}") }
                },
                style = MaterialTheme.typensity.bodyMedium
            )
            meetup.note?.let {
                Spacer(Modifier.height(8.dp))
                Text(it, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Composable
fun MeetupDetailScreen(
    modifier: Modifier = Modifier,
    currentUserId: String,
    meetup: Meetup,
    usersRepo: UsersRepositoryMock,
    meetupsRepo: MeetupsRepositoryMock,
    onBack: () -> Unit
) {
    val users by usersRepo.users.collectAsState(initial = emptyList())
    val usersById = remember(users) { users.associateBy { it.uid } }
    val host = usersById[meetup.hostUid]

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Meetup") },
                navigationIcon = {
                    TextButton(onClick = onBack) { Text("Back") }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(meetup.title, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            Text(formatRange(meetup.startAtEpochMs, meetup.endAtEpochMs), style = MaterialTheme.typography.bodyMedium)

            host?.let { Text("Host: ${it.displayName}", style = MaterialTheme.typography.bodyMedium) }
            Text("Place ID: ${meetup.placeId}", style = MaterialTheme.typography.bodySmall)

            meetup.note?.let {
                Divider(Modifier.padding(vertical = 4.dp))
                Text(it, style = MaterialTheme.typography.bodyMedium)
            }

            Divider()
            Text("RSVP", style = MaterialTheme.typography.titleSmall)
            val myStatus = meetup.rsvps[currentUserId]
            RsvpChipRow(
                selected = myStatus,
                onSelect = { newStatus ->
                    meetupsRepo.setRsvp(meetupId = meetup.id, uid = currentUserId, status = newStatus)
                }
            )

            Divider()
            Text("Invitees", style = MaterialTheme.typography.titleSmall)
            val inviteeRows = meetup.inviteeIds.map { id ->
                val name = usersById[id]?.displayName ?: id
                val status = meetup.rsvps[id]?.wire ?: "—"
                "$name  •  $status"
            }
            if (inviteeRows.isEmpty()) {
                Text("No invitees yet", style = MaterialTheme.typography.bodyMedium)
            } else {
                inviteeRows.forEach { row ->
                    Text(row, style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}

@Composable
private fun RsvpChipRow(
    selected: RsvpStatus?,
    onSelect: (RsvpStatus) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        FilterChip(
            selected = selected == RsvpStatus.GOING,
            onClick = { onSelect(RsvpStatus.GOING) },
            label = { Text("Going") }
        )
        FilterChip(
            selected = selected == RsvpStatus.RECEIVED,
            onClick = { onSelect(RsvpStatus.RECEIVED) },
            label = { Text("Received") }
        )
        FilterChip(
            selected = selected == RsvpStatus.INTERESTED,
            onClick = { onSelect(RsvpStatus.INTERESTED) },
            label = { Text("Interested") }
        )
    }
}

/** Friendly “date at time — date at time” using device locale */
private fun formatRange(startMs: Long, endMs: Long): String {
    val dfDate = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault())
    val dfTime = DateFormat.getTimeInstance(DateFormat.SHORT, Locale.getDefault())
    val start = Date(startMs)
    val end = Date(endMs)
    val startStr = "${dfDate.format(start)} ${dfTime.format(start)}"
    val endStr = "${dfDate.format(end)} ${dfTime.format(end)}"
    return "$startStr — $endStr"
}
