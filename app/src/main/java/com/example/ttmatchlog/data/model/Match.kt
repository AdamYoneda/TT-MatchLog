package com.example.ttmatchlog.data.model

import android.os.Parcel
import android.os.Parcelable

// 試合の詳細 (スコア、自分と相手の各セットの得点)
data class Match(
    val id: String = "",
    val roundNumber: Int = 0,                 // 試合の順序（1回戦、2回戦など）
    val playerScore: Int = 0,                 // 自分の総スコア
    val opponentScore: Int = 0,               // 相手の総スコア
    val opponentName: String = "",
    val gameScores: GameScores = GameScores(), // 各セットの詳細スコア
    val note: String = "",
    val tournamentId: String = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readParcelable(GameScores::class.java.classLoader) ?: GameScores(),
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeInt(roundNumber)
        parcel.writeInt(playerScore)
        parcel.writeInt(opponentScore)
        parcel.writeString(opponentName)
        parcel.writeParcelable(gameScores, flags)
        parcel.writeString(note)
        parcel.writeString(tournamentId)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Match> {
        override fun createFromParcel(parcel: Parcel): Match {
            return Match(parcel)
        }

        override fun newArray(size: Int): Array<Match?> {
            return arrayOfNulls(size)
        }
    }
}