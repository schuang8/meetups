package com.example.presence.data

data class PlaceAggregate(
    val id: String,
    val name: String,
    val countHere: Int,
    val countInterested: Int,
    val sampleEtaMins: Int? = null
)

class MockRepository {
    fun getNearby(): List<PlaceAggregate> = listOf(
        PlaceAggregate("p1", "Blue Bottle Coffee", 2, 3, 11),
        PlaceAggregate("p2", "City Park Courts", 1, 4, 18),
        PlaceAggregate("p3", "Ramen House", 0, 2, 9)
    )
}
