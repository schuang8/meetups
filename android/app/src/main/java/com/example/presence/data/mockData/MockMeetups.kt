package com.example.presence.data.mockData

import com.example.presence.data.Meetup
import com.example.presence.data.RsvpStatus
import com.example.presence.data.Visibility

val MockMeetupsSeed: List<Meetup> = listOf(
    Meetup(
        id = "m1_coffee_after_work",
        title = "Coffee after work",
        hostUid = "u_amy",
        placeId = "ch_123",
        startAtEpochMs = 1757802600000, endAtEpochMs = 1757806200000,
        inviteeIds = listOf("u_bob", "u_cara"),
        rsvps = mapOf("u_bob" to RsvpStatus.GOING, "u_cara" to RsvpStatus.INTERESTED),
        note = "Blue Bottle on Sansome",
        visibility = Visibility.FRIENDS,
        createdAtEpochMs = 1757793600000, updatedAtEpochMs = 1757793600000
    ),
    Meetup(
        id = "m2_lunch_midtown",
        title = "Quick lunch in Midtown",
        hostUid = "u_bob",
        placeId = "sm_200",
        startAtEpochMs = 1757846400000, endAtEpochMs = 1757850000000,
        inviteeIds = listOf("u_ella", "u_dan"),
        rsvps = mapOf("u_ella" to RsvpStatus.GOING, "u_dan" to RsvpStatus.RECEIVED),
        note = "Shake Shack on 8th",
        visibility = Visibility.FRIENDS,
        createdAtEpochMs = 1757793900000, updatedAtEpochMs = 1757793900000
    ),
    Meetup(
        id = "m3_central_park_run",
        title = "Morning run in Central Park",
        hostUid = "u_ivy",
        placeId = "cp_001",
        startAtEpochMs = 1757820600000, endAtEpochMs = 1757824200000,
        inviteeIds = listOf("u_jade", "u_hugo"),
        rsvps = mapOf("u_jade" to RsvpStatus.INTERESTED, "u_hugo" to RsvpStatus.GOING),
        note = "Meet at the Reservoir loop",
        visibility = Visibility.PUBLIC,
        createdAtEpochMs = 1757794200000, updatedAtEpochMs = 1757794200000
    ),
    Meetup(
        id = "m4_soho_cowork",
        title = "SoHo coworking session",
        hostUid = "u_dan",
        placeId = "soho_321",
        startAtEpochMs = 1757925600000, endAtEpochMs = 1757940000000,
        inviteeIds = listOf("u_amy", "u_felix"),
        rsvps = mapOf("u_amy" to RsvpStatus.GOING, "u_felix" to RsvpStatus.RECEIVED),
        note = "WeWork SoHo, 4F",
        visibility = Visibility.FRIENDS,
        createdAtEpochMs = 1757794320000, updatedAtEpochMs = 1757794320000
    ),
    Meetup(
        id = "m5_game_night",
        title = "West Village game night",
        hostUid = "u_gina",
        placeId = "wv_777",
        startAtEpochMs = 1757971200000, endAtEpochMs = 1757982000000,
        inviteeIds = listOf("u_ivy", "u_jade"),
        rsvps = mapOf("u_ivy" to RsvpStatus.GOING),
        note = "Bring snacks",
        visibility = Visibility.FRIENDS,
        createdAtEpochMs = 1757794440000, updatedAtEpochMs = 1757794440000
    ),
    Meetup(
        id = "m6_study_cafe",
        title = "Study session (LES)",
        hostUid = "u_felix",
        placeId = "les_888",
        startAtEpochMs = 1757853600000, endAtEpochMs = 1757860800000,
        inviteeIds = listOf("u_cara", "u_amy"),
        rsvps = mapOf("u_cara" to RsvpStatus.RECEIVED),
        note = "Caffe Vita on Ludlow",
        visibility = Visibility.FRIENDS,
        createdAtEpochMs = 1757794560000, updatedAtEpochMs = 1757794560000
    )
)
