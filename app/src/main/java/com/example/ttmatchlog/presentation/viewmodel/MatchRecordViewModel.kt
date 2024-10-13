package com.example.ttmatchlog.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ttmatchlog.data.model.Tournament
import com.example.ttmatchlog.data.repository.TournamentRepository
import kotlinx.coroutines.launch

// MatchRecordViewModel.kt
class MatchRecordViewModel(private val repository: TournamentRepository) : ViewModel() {

    private val _tournaments = MutableLiveData<List<Tournament>>()
    val tournaments: LiveData<List<Tournament>> get() = _tournaments

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
}