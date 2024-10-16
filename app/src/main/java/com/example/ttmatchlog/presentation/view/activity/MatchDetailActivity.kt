package com.example.ttmatchlog.presentation.view.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
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

        val user = UserManager.getUser()
        val match: Match? = intent.getParcelableExtra("EXTRA_MATCH")

        if (user == null || match == null) {
            Toast.makeText(this, "ユーザー情報または試合情報が取得できませんでした", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        viewModel.loadTournament(user.userId, match.tournamentId)
        // Tournament取得後にUIに反映
        viewModel.tournament.observe(this, Observer { tournament ->
            displayMatchDetails(match, tournament, user.userName)
        })
        // エラーメッセージの監視
        viewModel.error.observe(this, Observer { errorMessage ->
            if (errorMessage != null) {
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Matchの詳細をTextViewなどに表示する処理
    private fun displayMatchDetails(match: Match, tournament: Tournament, userName: String) {
        // 大会名等
        findViewById<TextView>(R.id.tournamentNameTextView).text = "${tournament.tournamentName} (${tournament.date})"
        findViewById<TextView>(R.id.roundNumberTextView).text = "${match.roundNumber}回戦"
        // 選手名
        findViewById<TextView>(R.id.playerNameTextView).text = userName
        findViewById<TextView>(R.id.opponentNameTextView).text = match.opponentName
        // スコア
        val playerScore = match.playerScore
        val opponentScore = match.opponentScore
        findViewById<TextView>(R.id.playerScore).text = playerScore.toString()
        findViewById<TextView>(R.id.opponentScore).text = opponentScore.toString()
        val roundNum = playerScore + opponentScore
        if (roundNum == 7) {
            findViewById<TextView>(R.id.playerScore1).text = match.gameScores.playerSet1.toString()
            findViewById<TextView>(R.id.opponentScore1).text = match.gameScores.opponentSet1.toString()
            findViewById<TextView>(R.id.playerScore2).text = match.gameScores.playerSet2.toString()
            findViewById<TextView>(R.id.opponentScore2).text = match.gameScores.opponentSet2.toString()
            findViewById<TextView>(R.id.playerScore3).text = match.gameScores.playerSet3.toString()
            findViewById<TextView>(R.id.opponentScore3).text = match.gameScores.opponentSet3.toString()
            findViewById<TextView>(R.id.playerScore4).text = match.gameScores.playerSet4.toString()
            findViewById<TextView>(R.id.opponentScore4).text = match.gameScores.opponentSet4.toString()
            findViewById<TextView>(R.id.playerScore5).text = match.gameScores.playerSet5.toString()
            findViewById<TextView>(R.id.opponentScore5).text = match.gameScores.opponentSet5.toString()
            findViewById<TextView>(R.id.playerScore6).text = match.gameScores.playerSet6.toString()
            findViewById<TextView>(R.id.opponentScore6).text = match.gameScores.opponentSet6.toString()
            findViewById<TextView>(R.id.playerScore7).text = match.gameScores.playerSet7.toString()
            findViewById<TextView>(R.id.opponentScore7).text = match.gameScores.opponentSet7.toString()
        } else if (roundNum == 6) {
            findViewById<LinearLayout>(R.id.scoreView7).visibility = View.GONE
            findViewById<TextView>(R.id.playerScore1).text = match.gameScores.playerSet1.toString()
            findViewById<TextView>(R.id.opponentScore1).text = match.gameScores.opponentSet1.toString()
            findViewById<TextView>(R.id.playerScore2).text = match.gameScores.playerSet2.toString()
            findViewById<TextView>(R.id.opponentScore2).text = match.gameScores.opponentSet2.toString()
            findViewById<TextView>(R.id.playerScore3).text = match.gameScores.playerSet3.toString()
            findViewById<TextView>(R.id.opponentScore3).text = match.gameScores.opponentSet3.toString()
            findViewById<TextView>(R.id.playerScore4).text = match.gameScores.playerSet4.toString()
            findViewById<TextView>(R.id.opponentScore4).text = match.gameScores.opponentSet4.toString()
            findViewById<TextView>(R.id.playerScore5).text = match.gameScores.playerSet5.toString()
            findViewById<TextView>(R.id.opponentScore5).text = match.gameScores.opponentSet5.toString()
            findViewById<TextView>(R.id.playerScore6).text = match.gameScores.playerSet6.toString()
            findViewById<TextView>(R.id.opponentScore6).text = match.gameScores.opponentSet6.toString()
        } else {
            findViewById<LinearLayout>(R.id.scoreView7).visibility = View.GONE
            findViewById<LinearLayout>(R.id.scoreView6).visibility = View.GONE
            findViewById<TextView>(R.id.playerScore1).text = match.gameScores.playerSet1.toString()
            findViewById<TextView>(R.id.opponentScore1).text = match.gameScores.opponentSet1.toString()
            findViewById<TextView>(R.id.playerScore2).text = match.gameScores.playerSet2.toString()
            findViewById<TextView>(R.id.opponentScore2).text = match.gameScores.opponentSet2.toString()
            findViewById<TextView>(R.id.playerScore3).text = match.gameScores.playerSet3.toString()
            findViewById<TextView>(R.id.opponentScore3).text = match.gameScores.opponentSet3.toString()
            findViewById<TextView>(R.id.playerScore4).text = match.gameScores.playerSet4.toString()
            findViewById<TextView>(R.id.opponentScore4).text = match.gameScores.opponentSet4.toString()
            findViewById<TextView>(R.id.playerScore5).text = match.gameScores.playerSet5.toString()
            findViewById<TextView>(R.id.opponentScore5).text = match.gameScores.opponentSet5.toString()
        }
        // note
        findViewById<TextView>(R.id.noteTextView).text = match.note
    }
}