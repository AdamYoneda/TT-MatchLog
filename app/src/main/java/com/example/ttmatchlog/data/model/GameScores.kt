package com.example.ttmatchlog.data.model

// 各ゲームセットのスコア (最大7セットをサポート)
data class GameScores(
    val playerSet1: Int,                  // 自分の1セット目の得点
    val opponentSet1: Int,                // 相手の1セット目の得点
    val playerSet2: Int,                  // 自分の2セット目の得点
    val opponentSet2: Int,                // 相手の2セット目の得点
    val playerSet3: Int,                  // 自分の3セット目の得点
    val opponentSet3: Int,                // 相手の3セット目の得点
    val playerSet4: Int?,                 // 自分の4セット目の得点（オプション）
    val opponentSet4: Int?,               // 相手の4セット目の得点（オプション）
    val playerSet5: Int?,                 // 自分の5セット目の得点（オプション）
    val opponentSet5: Int?,               // 相手の5セット目の得点（オプション)
    val playerSet6: Int?,                 // 自分の6セット目の得点（オプション）
    val opponentSet6: Int?,               // 相手の6セット目の得点（オプション）
    val playerSet7: Int?,                 // 自分の7セット目の得点（オプション）
    val opponentSet7: Int?                // 相手の7セット目の得点（オプション）
)