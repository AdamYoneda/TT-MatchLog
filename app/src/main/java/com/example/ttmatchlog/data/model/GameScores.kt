package com.example.ttmatchlog.data.model

import android.os.Parcel
import android.os.Parcelable

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
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(), parcel.readInt(),
        parcel.readInt(), parcel.readInt(),
        parcel.readInt(), parcel.readInt(),
        parcel.readInt(), parcel.readInt(),
        parcel.readInt(), parcel.readInt(),
        parcel.readInt(), parcel.readInt(),
        parcel.readInt(), parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(playerSet1)
        parcel.writeInt(opponentSet1)
        parcel.writeInt(playerSet2)
        parcel.writeInt(opponentSet2)
        parcel.writeInt(playerSet3)
        parcel.writeInt(opponentSet3)
        parcel.writeInt(playerSet4)
        parcel.writeInt(opponentSet4)
        parcel.writeInt(playerSet5)
        parcel.writeInt(opponentSet5)
        parcel.writeInt(playerSet6)
        parcel.writeInt(opponentSet6)
        parcel.writeInt(playerSet7)
        parcel.writeInt(opponentSet7)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<GameScores> {
        override fun createFromParcel(parcel: Parcel): GameScores {
            return GameScores(parcel)
        }

        override fun newArray(size: Int): Array<GameScores?> {
            return arrayOfNulls(size)
        }
    }
}