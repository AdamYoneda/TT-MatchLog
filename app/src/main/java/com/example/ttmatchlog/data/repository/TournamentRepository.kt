package com.example.ttmatchlog.data.repository

import android.util.Log
import com.example.ttmatchlog.data.model.Match
import com.example.ttmatchlog.data.model.Tournament
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.tasks.await

// TournamentRepository.kt
class TournamentRepository {
    private val db = FirebaseFirestore.getInstance()

    suspend fun fetchTournaments(userId: String): List<Tournament> {
        val tournamentList = mutableListOf<Tournament>()

        try {
            val tournamentSnapshots = db.collection("users")
                .document(userId)
                .collection("tournaments")
                .get()
                .await()

            for (tournamentDoc in tournamentSnapshots) {
                val tournament = tournamentDoc.toObject(Tournament::class.java)

                val matchSnapshots = tournamentDoc.reference.collection("matches").get().await()
                val matches = matchSnapshots.toObjects(Match::class.java)

                tournamentList.add(tournament.copy(matches = matches))
            }
        } catch (e: FirebaseFirestoreException) {
            Log.e("TournamentRepository", "Firestore取得エラー: ${e.message}")
        } catch (e: Exception) {
            Log.e("TournamentRepository", "不明なエラー: ${e.message}")
        }

        return tournamentList
    }
}