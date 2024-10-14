package com.example.ttmatchlog.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ttmatchlog.data.model.Tournament
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class MatchDetailViewModel : ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()

    private val _tournament = MutableLiveData<Tournament>()
    val tournament: LiveData<Tournament> get() = _tournament

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    fun loadTournament(userId: String, tournamentId: String) {
        viewModelScope.launch {
            try {
                val document = firestore.collection("users")
                    .document(userId)
                    .collection("tournaments")
                    .document(tournamentId)
                    .get()
                    .await()

                if (document.exists()) {
                    val tournament = document.toObject(Tournament::class.java)
                    _tournament.value = tournament
                } else {
                    _error.value = "大会情報が見つかりません"
                }
            } catch (e: Exception) {
                _error.value = "エラーが発生しました: ${e.message}"
            }
        }
    }
}