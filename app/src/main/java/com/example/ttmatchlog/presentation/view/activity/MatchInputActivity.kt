package com.example.ttmatchlog.presentation.view.activity

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.ttmatchlog.R
import com.example.ttmatchlog.data.model.GameScores
import com.example.ttmatchlog.data.model.Match
import com.example.ttmatchlog.presentation.view.adapter.MatchInputAdapter
import java.util.UUID

class MatchInputActivity : AppCompatActivity() {
    private val matchList = mutableListOf<Match>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_input)

        // Viewの取得
        val matchListView: ListView = findViewById(R.id.matchListView)
        val addItemButton: Button = findViewById(R.id.addItemButton)
        val confirmButton: Button = findViewById(R.id.confirmButton)

        // 1. Adapterにカスタムレイアウトをセット
        val adapter: MatchInputAdapter = MatchInputAdapter(this,matchList)
        matchListView.adapter = adapter

        // 2. アイテム追加ボタンの処理
        addItemButton.setOnClickListener {
            showMatchInputDialog()
        }
    }

    private fun showMatchInputDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_match_input, null)

        // Spinnerにデータをセット（ゲーム数）
        val scoreRange = (0..7).toList()
        val scoreAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, scoreRange)
        scoreAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val playerScoreSpinner = dialogView.findViewById<Spinner>(R.id.spinner_player_game_score)
        playerScoreSpinner.adapter = scoreAdapter
        val opponentScoreSpinner =
            dialogView.findViewById<Spinner>(R.id.spinner_opponent_game_score)
        opponentScoreSpinner.adapter = scoreAdapter
        // Spinnerにデータをセット（各ゲームの点数）
        val gameScoreRange = (0..25).toList()
        val gameScoreAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, gameScoreRange)
        gameScoreAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val playerScoreSpinners = arrayOf(
            dialogView.findViewById<Spinner>(R.id.spinner_player_game1),
            dialogView.findViewById<Spinner>(R.id.spinner_player_game2),
            dialogView.findViewById<Spinner>(R.id.spinner_player_game3),
            dialogView.findViewById<Spinner>(R.id.spinner_player_game4),
            dialogView.findViewById<Spinner>(R.id.spinner_player_game5),
            dialogView.findViewById<Spinner>(R.id.spinner_player_game6),
            dialogView.findViewById<Spinner>(R.id.spinner_player_game7)
        )
        val opponentScoreSpinners = arrayOf(
            dialogView.findViewById<Spinner>(R.id.spinner_opponent_game1),
            dialogView.findViewById<Spinner>(R.id.spinner_opponent_game2),
            dialogView.findViewById<Spinner>(R.id.spinner_opponent_game3),
            dialogView.findViewById<Spinner>(R.id.spinner_opponent_game4),
            dialogView.findViewById<Spinner>(R.id.spinner_opponent_game5),
            dialogView.findViewById<Spinner>(R.id.spinner_opponent_game6),
            dialogView.findViewById<Spinner>(R.id.spinner_opponent_game7)
        )
        for (i in playerScoreSpinners.indices) {
            playerScoreSpinners[i].adapter = gameScoreAdapter
            opponentScoreSpinners[i].adapter = gameScoreAdapter
        }

        // ダイアログを構築
        AlertDialog.Builder(this)
            .setTitle("新しい試合を記録")
            .setView(dialogView)
            .setPositiveButton("OK") { dialog, _ ->
//                // 入力データを取得
//                val opponentName = opponentNameEditText.text.toString()
//                val playerScore = playerScoreEditText.text.toString().toIntOrNull() ?: 0
//                val opponentScore = opponentScoreEditText.text.toString().toIntOrNull() ?: 0
//                val note = noteEditText.text.toString()
//
//                // 各セットのスコアを取得
//                val playerSet1 = playerSet1EditText.text.toString().toIntOrNull() ?: 0
//                val opponentSet1 = opponentSet1EditText.text.toString().toIntOrNull() ?: 0
//                val playerSet2 = playerSet2EditText.text.toString().toIntOrNull() ?: 0
//                val opponentSet2 = opponentSet2EditText.text.toString().toIntOrNull() ?: 0
//                val playerSet3 = playerSet1EditText.text.toString().toIntOrNull() ?: 0
//                val opponentSet3 = opponentSet1EditText.text.toString().toIntOrNull() ?: 0
//
//                // GameScoresの作成
//                val gameScores = GameScores(
//                    playerSet1 = playerSet1,
//                    opponentSet1 = opponentSet1,
//                    playerSet2 = null, // 他のセットはオプションに応じて値を設定
//                    opponentSet2 = null,
//                    playerSet3 = null,
//                    opponentSet3 = null,
//                    playerSet4 = null,
//                    opponentSet4 = null,
//                    playerSet5 = null,
//                    opponentSet5 = null,
//                    playerSet6 = null,
//                    opponentSet6 = null,
//                    playerSet7 = null,
//                    opponentSet7 = null
//                )
//
//                // UUIDを生成し、Matchオブジェクトを作成
//                val newMatch = Match(
//                    id = UUID.randomUUID().toString(),
//                    roundNumber = calculateRoundNumber(), // ラウンド番号は自動計算
//                    playerScore = playerScore,
//                    opponentScore = opponentScore,
//                    opponentName = opponentName,
//                    gameScores = gameScores,
//                    note = note
//                )
//
//                // 作成されたMatchオブジェクトを使用して、データを保存したり表示したりする
//                matchList.add(newMatch)
//                adapter.notifyDataSetChanged() // ListViewを更新

                dialog.dismiss()
            }
            .setNegativeButton("キャンセル") { dialog, _ ->
                dialog.dismiss()
            }
            .show()

        val opponentNameEditText = dialogView.findViewById<EditText>(R.id.edit_opponent_name)
        opponentNameEditText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val inputText = opponentNameEditText.text.toString().trim()

                if (inputText.isEmpty()) {
                    // 入力がホワイトスペースのみの場合、エラーメッセージを表示
                    opponentNameEditText.error = "名前を正しく入力してください"
                } else {
                    // キーボードを閉じる
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.windowToken, 0)
                }
                true
            } else {
                false
            }
        }
    }

    private fun calculateRoundNumber(): Int {
        // ラウンド番号を計算するロジック
        return 1 // 例: 1回戦目
    }

    private fun saveMatch(match: Match) {
        // Matchを保存する処理 (Firestoreなど)
    }
}