package com.example.ttmatchlog.presentation.view.activity

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ttmatchlog.R
import com.example.ttmatchlog.data.model.Match
import com.example.ttmatchlog.data.model.Tournament

class MatchDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_detail)

        // Intentからデータを取得
        val match: Match? = intent.getParcelableExtra("EXTRA_MATCH")

        if (match == null) {
            Toast.makeText(this, "データの取得に失敗しました", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        displayMatchDetails(match)
    }

    // TODO: ここの詳細は後で修正
    private fun displayMatchDetails(match: Match) {
        // Matchの詳細をTextViewなどに表示する処理
        findViewById<TextView>(R.id.roundNumberTextView).text = "${match.roundNumber}回戦"
        findViewById<TextView>(R.id.opponentNameTextView).text = match.opponentName
        findViewById<TextView>(R.id.noteTextView).text = if (match.note.isNotEmpty()) match.note else "備考なし"
    }
}