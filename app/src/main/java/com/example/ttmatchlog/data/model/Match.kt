package com.example.ttmatchlog.data.model

// 試合の詳細 (スコア、自分と相手の各セットの得点)
data class Match(
    val id: String = "",
    val roundNumber: Int = 0,                 // 試合の順序（1回戦、2回戦など）
    val playerScore: Int = 0,                 // 自分の総スコア
    val opponentScore: Int = 0,               // 相手の総スコア
    val opponentName: String = "",
    val gameScores: GameScores = GameScores(),            // 各セットの詳細スコア
    val note: String = ""
)