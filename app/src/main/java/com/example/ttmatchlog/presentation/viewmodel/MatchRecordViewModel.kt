package com.example.ttmatchlog.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ttmatchlog.data.model.MatchType
import com.example.ttmatchlog.data.model.Tournament
import com.example.ttmatchlog.data.repository.TournamentRepository

// MatchRecordViewModel.kt
class MatchRecordViewModel(private val repository: TournamentRepository) : ViewModel() {

    private val _tournaments = MutableLiveData<List<Tournament>>()
    val tournaments: LiveData<List<Tournament>> get() = _tournaments

    // データの取得とソートを行う
    fun loadTournaments() {
        val tournamentsList = repository.fetchTournaments()
        val matchTypeComparator = compareBy<Tournament> { tournament ->
            when (tournament.matchType) {
                MatchType.SINGLES -> 1
                MatchType.DOUBLES -> 2
                MatchType.TEAM -> 3
            }
        }

        // 日付で降順ソートし、さらにmatchTypeでソート
        val sortedTournaments = tournamentsList
            .sortedWith(compareByDescending<Tournament> { it.date }.then(matchTypeComparator))
            .map { tournament ->
                tournament.copy(matches = tournament.matches.sortedBy { it.roundNumber })
            }

        _tournaments.value = sortedTournaments
    }
}