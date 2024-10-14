package com.example.ttmatchlog.presentation.view.activity

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.ttmatchlog.R
import com.example.ttmatchlog.data.model.Match
import com.example.ttmatchlog.data.model.Tournament
import com.example.ttmatchlog.presentation.viewmodel.MatchDetailViewModel
import com.example.ttmatchlog.utils.UserManager

class MatchDetailActivity : AppCompatActivity() {
    private val viewModel: MatchDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_detail)

        val userId = UserManager.getUser()?.userId
        val match: Match? = intent.getParcelableExtra("EXTRA_MATCH")

        if (userId == null || match == null) {
            Toast.makeText(this, "ユーザー情報または試合情報が取得できませんでした", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        viewModel.loadTournament(userId, match.tournamentId)
        // Tournament取得後にUIに反映
        viewModel.tournament.observe(this, Observer { tournament ->
            displayMatchDetails(match, tournament)
        })
        // エラーメッセージの監視
        viewModel.error.observe(this, Observer { errorMessage ->
            if (errorMessage != null) {
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

    // TODO: ここの詳細は後で修正
    private fun displayMatchDetails(match: Match, tournament: Tournament) {
        // Matchの詳細をTextViewなどに表示する処理
        findViewById<TextView>(R.id.tournamentNameTextView).text = "${tournament.tournamentName} (${tournament.date})"
        findViewById<TextView>(R.id.roundNumberTextView).text = "${match.roundNumber}回戦"
        findViewById<TextView>(R.id.opponentNameTextView).text = match.opponentName
        findViewById<TextView>(R.id.noteTextView).text = if (match.note.isNotEmpty()) match.note else "備考なし"
    }
}