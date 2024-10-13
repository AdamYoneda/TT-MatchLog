package com.example.ttmatchlog.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ttmatchlog.data.model.Match
import com.example.ttmatchlog.data.model.Tournament
import com.example.ttmatchlog.utils.UserManager
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class MatchInputViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()

    private val _uploadStatus = MutableLiveData<Boolean>()
    val uploadStatus: LiveData<Boolean> get() = _uploadStatus

    fun saveTournament(tournament: Tournament, matchList: List<Match>) {
        val userId = UserManager.getUser()?.userId
        if (userId == null) {
            Log.e("UserManager", "User ID not found")
            _uploadStatus.postValue(false) // 失敗通知
            return
        }

        val tournamentRef = db.collection("users")
            .document(userId)
            .collection("tournaments")
            .document(tournament.id)

        tournamentRef.set(tournament)
            .addOnSuccessListener {
                Log.d("Firestore", "Tournament successfully uploaded.")
                uploadMatches(tournamentRef, matchList)
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Error uploading tournament", e)
                _uploadStatus.postValue(false) // 失敗通知
            }
    }

    private fun uploadMatches(tournamentRef: DocumentReference, matchList: List<Match>) {
        val tasks = matchList.map { match ->
            tournamentRef.collection("matches").document(match.id).set(match)
        }

        Tasks.whenAllComplete(tasks).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("Firestore", "All matches successfully uploaded.")
                _uploadStatus.postValue(true) // 成功通知
            } else {
                Log.e("Firestore", "Some matches failed to upload.", task.exception)
                _uploadStatus.postValue(false) // 失敗通知
            }
        }
    }
}