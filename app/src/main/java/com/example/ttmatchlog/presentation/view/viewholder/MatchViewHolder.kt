package com.example.ttmatchlog.presentation.view.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ttmatchlog.R
import com.example.ttmatchlog.data.model.Match

class MatchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(match: Match) {
        // 試合のデータをバインド
        itemView.findViewById<TextView>(R.id.opponent_name).text = "${match.roundNumber}回戦 ${match.opponentName}"
        itemView.findViewById<TextView>(R.id.score).text =
            "${match.playerScore} - ${match.opponentScore}"
    }
}