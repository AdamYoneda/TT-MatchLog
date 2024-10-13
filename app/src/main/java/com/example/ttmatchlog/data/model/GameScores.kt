package com.example.ttmatchlog.data.model

// 各ゲームセットのスコア (最大7セットをサポート)
data class GameScores(
    val playerSet1: Int = 0,
    val opponentSet1: Int = 0,
    val playerSet2: Int = 0,
    val opponentSet2: Int = 0,
    val playerSet3: Int = 0,
    val opponentSet3: Int = 0,
    val playerSet4: Int = 0,
    val opponentSet4: Int = 0,
    val playerSet5: Int = 0,
    val opponentSet5: Int = 0,
    val playerSet6: Int = 0,
    val opponentSet6: Int = 0,
    val playerSet7: Int = 0,
    val opponentSet7: Int = 0
)