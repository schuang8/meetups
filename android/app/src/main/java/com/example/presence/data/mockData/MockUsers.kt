package com.example.presence.data.mockData

import com.example.presence.data.*

// NYC-ish coordinates, purely mocked
val MockUsers: List<User> = listOf(
    User(
        uid = "u_amy",
        displayName = "Amy Chen",
        username = "amyc",
        photoUrl = "https://example.com/avatars/amy.jpg",
        visibility = Visibility.FRIENDS,
        currentLocation = GeoPointLite(40.73061, -73.93524), // East Village
        updatedAtEpochMs = 1726128000000
    ),
    User(
        uid = "u_bob",
        displayName = "Bob Lee",
        username = "boblee",
        photoUrl = "https://example.com/avatars/bob.jpg",
        visibility = Visibility.PUBLIC,
        currentLocation = GeoPointLite(40.74105, -73.98970), // Flatiron
        updatedAtEpochMs = 1726128300000
    ),
    User(
        uid = "u_cara",
        displayName = "Cara Patel",
        username = "carap",
        photoUrl = "https://example.com/avatars/cara.jpg",
        visibility = Visibility.PRIVATE,
        currentLocation = GeoPointLite(40.70600, -74.00900), // FiDi
        updatedAtEpochMs = 1726128600000
    ),
    User(
        uid = "u_dan",
        displayName = "Dan Nguyen",
        username = "dann",
        photoUrl = "https://example.com/avatars/dan.jpg",
        visibility = Visibility.FRIENDS,
        currentLocation = GeoPointLite(40.71280, -74.00600), // City Hall
        updatedAtEpochMs = 1726128900000
    ),
    User(
        uid = "u_ella",
        displayName = "Ella Johnson",
        username = "ellaj",
        photoUrl = "https://example.com/avatars/ella.jpg",
        visibility = Visibility.PUBLIC,
        currentLocation = GeoPointLite(40.75800, -73.98550), // Times Sq
        updatedAtEpochMs = 1726129200000
    ),
    User(
        uid = "u_felix",
        displayName = "Felix Garcia",
        username = "felixg",
        photoUrl = "https://example.com/avatars/felix.jpg",
        visibility = Visibility.FRIENDS,
        currentLocation = GeoPointLite(40.72230, -73.98740), // Lower East Side
        updatedAtEpochMs = 1726129500000
    ),
    User(
        uid = "u_gina",
        displayName = "Gina Park",
        username = "ginap",
        photoUrl = "https://example.com/avatars/gina.jpg",
        visibility = Visibility.PRIVATE,
        currentLocation = GeoPointLite(40.76777, -73.97183), // Upper East
        updatedAtEpochMs = 1726129800000
    ),
    User(
        uid = "u_hugo",
        displayName = "Hugo Santos",
        username = "hugos",
        photoUrl = "https://example.com/avatars/hugo.jpg",
        visibility = Visibility.PUBLIC,
        currentLocation = GeoPointLite(40.67818, -73.94416), // Bed-Stuy
        updatedAtEpochMs = 1726130100000
    ),
    User(
        uid = "u_ivy",
        displayName = "Ivy Ross",
        username = "ivyross",
        photoUrl = "https://example.com/avatars/ivy.jpg",
        visibility = Visibility.FRIENDS,
        currentLocation = GeoPointLite(40.73000, -74.00000), // Greenwich Village
        updatedAtEpochMs = 1726130400000
    ),
    User(
        uid = "u_jade",
        displayName = "Jade Kim",
        username = "jadek",
        photoUrl = "https://example.com/avatars/jade.jpg",
        visibility = Visibility.PUBLIC,
        currentLocation = GeoPointLite(40.70657, -73.93001), // Bushwick
        updatedAtEpochMs = 1726130700000
    )
)
