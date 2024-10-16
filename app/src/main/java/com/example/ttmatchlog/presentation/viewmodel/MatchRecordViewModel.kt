package com.example.ttmatchlog.presentation.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ttmatchlog.data.model.Tournament
import com.example.ttmatchlog.data.repository.ImageRepository
import com.example.ttmatchlog.data.repository.TournamentRepository
import com.example.ttmatchlog.utils.UserManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

// MatchRecordViewModel.kt
class MatchRecordViewModel(private val repository: TournamentRepository) : ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth
    private val _tournaments = MutableLiveData<List<Tournament>>()
    val tournaments: LiveData<List<Tournament>> get() = _tournaments

    private val _userIconUri = MutableLiveData<Uri>()
    val userIconUri: LiveData<Uri> get() = _userIconUri

    // データの取得とソートを行う
    fun loadTournaments(userId: String) {
        viewModelScope.launch {
            val tournamentsList = repository.fetchTournaments(userId)
            val sortedTournaments = tournamentsList
                .sortedWith(compareByDescending<Tournament> { it.date }
                    .thenBy { it.matchType.ordinal })
                .map { it.copy(matches = it.matches.sortedBy { match -> match.roundNumber }) }

            _tournaments.value = sortedTournaments
        }
    }

    fun downloadImage(url: String, imageRepository: ImageRepository) {
        imageRepository.downloadImageToUri(url) { uri ->
            _userIconUri.postValue(uri)
        }
    }

    fun signout() {
        auth.signOut()
        UserManager.clearUser()
    }

    fun checkUserIsLogout(): Boolean {
        return auth.currentUser == null
    }
}