package com.example.presence.data

import com.example.presence.data.mockData.MockMeetupsSeed
import kotlinx.coroutines.flow.MutableStateFlow

class MockRepository {
    private val _meetups = MutableStateFlow(MockMeetupsSeed)

    fun getNearby(): List<PlaceAggregate> = listOf(
        PlaceAggregate("p1", "Blue Bottle Coffee", 2, 3, 11),
        PlaceAggregate("p2", "City Park Courts", 1, 4, 18),
        PlaceAggregate("p3", "Ramen House", 0, 2, 9)
    )

    fun setRsvp(meetupId: String, uid: String, status: RsvpStatus) {
        _meetups.value = _meetups.value.map { m ->
            if (m.id == meetupId) m.copy(
                rsvps = m.rsvps.toMutableMap().apply { this[uid] = status }
            ) else m
        }
    }
}
