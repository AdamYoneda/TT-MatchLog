package com.example.ttmatchlog.data.model

// 試合形式の列挙型にStringの値を持たせる
enum class MatchType(val type: String) {
    SINGLES("シングルス"),
    DOUBLES("ダブルス"),
    TEAM("団体戦");

    // String型の値を取得する関数
    fun getValue(): String {
        return type
    }
}