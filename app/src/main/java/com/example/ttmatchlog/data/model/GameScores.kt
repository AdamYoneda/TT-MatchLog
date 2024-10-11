package com.example.ttmatchlog.data.model

// 各ゲームセットのスコア (最大7セットをサポート)
data class GameScores(
    val playerSet1: Int,
    val opponentSet1: Int,
    val playerSet2: Int,
    val opponentSet2: Int,
    val playerSet3: Int,
    val opponentSet3: Int,
    val playerSet4: Int?,
    val opponentSet4: Int,
    val playerSet5: Int,
    val opponentSet5: Int,
    val playerSet6: Int,
    val opponentSet6: Int,
    val playerSet7: Int,
    val opponentSet7: Int
)