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

    private fun formatGameScores(match: Match): String {
        val gameScores = match.gameScores
        val scores = mutableListOf<String>()
        if (gameScores.playerSet1 != null && gameScores.opponentSet1 != null)
            scores.add("Game1: ${gameScores.playerSet1}-${gameScores.opponentSet1}")
        if (gameScores.playerSet2 != null && gameScores.opponentSet2 != null)
            scores.add("Game2: ${gameScores.playerSet2}-${gameScores.opponentSet2}")
        if (gameScores.playerSet3 != null && gameScores.opponentSet3 != null)
            scores.add("Game3: ${gameScores.playerSet3}-${gameScores.opponentSet3}")
        if (gameScores.playerSet4 != null && gameScores.opponentSet4 != null)
            scores.add("Game4: ${gameScores.playerSet4}-${gameScores.opponentSet4}")
        if (gameScores.playerSet5 != null && gameScores.opponentSet5 != null)
            scores.add("Game5: ${gameScores.playerSet5}-${gameScores.opponentSet5}")
        if (gameScores.playerSet6 != null && gameScores.opponentSet6 != null)
            scores.add("Game5: ${gameScores.playerSet6}-${gameScores.opponentSet6}")
        if (gameScores.playerSet7 != null && gameScores.opponentSet7 != null)
            scores.add("Game7: ${gameScores.playerSet7}-${gameScores.opponentSet7}")
        // Alternatively, loop through sets
        return scores.joinToString(", ")
    }
}