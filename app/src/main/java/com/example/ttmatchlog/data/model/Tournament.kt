package com.example.ttmatchlog.data.model

import android.os.Parcel
import android.os.Parcelable

// 大会セクション (日付、試合形式、大会名、試合リスト)
data class Tournament(
    val id: String,
    val date: String,                     // 大会日付
    val tournamentName: String,           // 大会名
    val matchType: MatchType,             // 試合形式（シングルス、ダブルス、団体戦）
    val matches: List<Match> = emptyList()              // 試合リスト
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        MatchType.valueOf(parcel.readString() ?: ""),
        mutableListOf<Match>().apply {
            parcel.readList(this, Match::class.java.classLoader)
        }
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(date)
        parcel.writeString(tournamentName)
        parcel.writeString(matchType.name)
        parcel.writeList(matches)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<Tournament> {
        override fun createFromParcel(parcel: Parcel) = Tournament(parcel)
        override fun newArray(size: Int) = arrayOfNulls<Tournament>(size)
    }
}