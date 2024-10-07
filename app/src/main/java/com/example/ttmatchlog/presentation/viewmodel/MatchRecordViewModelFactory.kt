package com.example.ttmatchlog.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ttmatchlog.data.repository.TournamentRepository

class MatchRecordViewModelFactory(private val repository: TournamentRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MatchRecordViewModel::class.java)) {
            return MatchRecordViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}