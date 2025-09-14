package com.example.presence.data

enum class RsvpStatus(val wire: String) {
    GOING("going"),
    RECEIVED("received"),
    INTERESTED("interested");

    companion object {
        fun parse(s: String?): RsvpStatus? =
            values().firstOrNull { it.wire.equals(s, ignoreCase = true) }
    }
}

/** Minimal meetup model for the POC. */
data class Meetup(
    val id: String,
    val title: String,
    val hostUid: String,
    val placeId: String,
    val startAtEpochMs: Long,
    val endAtEpochMs: Long,
    val inviteeIds: List<String>,
    /** rsvps: uid -> going|received|interested */
    val rsvps: Map<String, RsvpStatus> = emptyMap(),
    val note: String? = null,
    val visibility: Visibility = Visibility.FRIENDS,
    val createdAtEpochMs: Long,
    val updatedAtEpochMs: Long
)
