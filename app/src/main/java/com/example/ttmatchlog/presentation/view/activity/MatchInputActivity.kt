package com.example.ttmatchlog.presentation.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.ttmatchlog.R
import com.example.ttmatchlog.data.model.GameScores
import com.example.ttmatchlog.data.model.Match
import com.example.ttmatchlog.data.model.Tournament
import com.example.ttmatchlog.presentation.view.adapter.MatchInputAdapter
import com.example.ttmatchlog.presentation.viewmodel.MatchInputViewModel
import com.example.ttmatchlog.utils.UserManager
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import java.util.UUID

class MatchInputActivity : AppCompatActivity() {
    private val matchList = mutableListOf<Match>()
    private var roundNumber: Int = 1
    private lateinit var tournament: Tournament

    private val viewModel: MatchInputViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_input)

        // Viewの取得
        val matchListView: ListView = findViewById(R.id.matchListView)
        val addItemButton: Button = findViewById(R.id.addItemButton)
        val confirmButton: Button = findViewById(R.id.confirmButton)

        // 0. 入力したTournament情報を取得
        // IntentからTournamentデータを取得
        tournament = intent.getParcelableExtra("tournament") ?: return

        // 1. Adapterにカスタムレイアウトをセット
        val adapter = MatchInputAdapter(this, matchList)
        matchListView.adapter = adapter

        // 2. アイテム追加ボタンの処理
        addItemButton.setOnClickListener {
            showMatchInputDialog(adapter, tournament.id)
        }

        // 3. 記録するボタンの処理
        confirmButton.setOnClickListener {
            viewModel.saveTournament(tournament, matchList)
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.uploadStatus.observe(this) { isSuccess ->
            if (isSuccess) {
                showToast("記録に成功しました。")
            } else {
                showToast("記録に失敗しました。時間を置いて再度試してください。")
            }
            navigateBackToMatchRecordActivity()
        }
    }

    private fun showMatchInputDialog(adapter: MatchInputAdapter, tournamentId: String) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_match_input, null)

        // Spinnerにデータをセット（ゲーム数）
        val scoreRange = (0..4).toList()
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
                // EditText から文字列を取得
                val inputOpponentNameEditText =
                    dialogView.findViewById<EditText>(R.id.edit_opponent_name)
                val inputOpponentName = inputOpponentNameEditText.text.toString().trim()
                val inputNote =
                    dialogView.findViewById<EditText>(R.id.edit_note).text.toString().trim()

                // Spinner からゲームスコアを取得
                val playerGameScore = playerScoreSpinner.selectedItem as Int
                val opponentGameScore = opponentScoreSpinner.selectedItem as Int

                // Spinner から各ラウンドの点数を取得
                val playerScores = playerScoreSpinners.map { it.selectedItem as Int }.toIntArray()
                val opponentScores =
                    opponentScoreSpinners.map { it.selectedItem as Int }.toIntArray()

                // 各フィールドが正しく入力されているかチェック
                if (inputOpponentName.isEmpty()) {
                    inputOpponentNameEditText.error = "名前を入力してください"
                } else {
                    // Match オブジェクトを作成
                    val newMatch = Match(
                        id = UUID.randomUUID().toString(),
                        roundNumber = roundNumber,
                        playerScore = playerGameScore,
                        opponentScore = opponentGameScore,
                        opponentName = inputOpponentName,
                        gameScores = GameScores(
                            playerScores[0], opponentScores[0],
                            playerScores[1], opponentScores[1],
                            playerScores[2], opponentScores[2],
                            playerScores[3], opponentScores[3],
                            playerScores[4], opponentScores[4],
                            playerScores[5], opponentScores[5],
                            playerScores[6], opponentScores[6]
                        ),
                        note = inputNote,
                        tournamentId = tournamentId
                    )
                    // 作成されたMatchオブジェクトを使用して、データを保存したり表示したりする
                    matchList.add(newMatch)
                    adapter.notifyDataSetChanged() // ListViewを更新
                    roundNumber += 1
                }
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
                    val imm =
                        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.windowToken, 0)
                }
                true
            } else {
                false
            }
        }
    }

    // Toastメッセージを表示する関数
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    // MatchRecordActivityに戻る関数
    private fun navigateBackToMatchRecordActivity() {
        val intent = Intent(this, MatchRecordActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish() // このActivityを終了
    }
}
