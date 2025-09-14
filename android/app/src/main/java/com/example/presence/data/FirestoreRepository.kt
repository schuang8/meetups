package com.example.presence.data

import com.google.firebase.Firebase
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class FirestoreRepository(
    private val db: com.google.firebase.firestore.FirebaseFirestore = Firebase.firestore
) {
    /**
     * Streams the top 10 place aggregates, most recently updated first.
     * Emits again whenever Firestore data changes.
     */
    fun nearbyFlow(): Flow<List<PlaceAggregate>> = callbackFlow {
        val registration = db.collection("place_aggregates")
            .orderBy("updatedAt", Query.Direction.DESCENDING)
            .limit(10)
            .addSnapshotListener { snap, err ->
                if (err != null) {
                    // Emit empty on error; you can also close(err) to surface it
                    trySend(emptyList())
                    return@addSnapshotListener
                }
                val items = snap?.documents?.map { d ->
                    PlaceAggregate(
                        id = d.id,
                        name = d.getString("name") ?: "Unknown",
                        countHere = (d.getLong("countHere") ?: 0L).toInt(),
                        countInterested = (d.getLong("countInterested") ?: 0L).toInt(),
                        sampleEtaMins = (d.getLong("sampleEtaMins") ?: 0L).toInt().let { if (it == 0) null else it }
                    )
                }.orEmpty()
                trySend(items)
            }
        awaitClose { registration.remove() }
    }
}
