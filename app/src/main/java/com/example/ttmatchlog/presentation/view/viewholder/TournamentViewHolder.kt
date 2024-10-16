package com.example.ttmatchlog.presentation.view.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ttmatchlog.R
import com.example.ttmatchlog.data.model.Tournament

class TournamentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(tournament: Tournament) {
        // 各大会のデータをバインド
        itemView.findViewById<TextView>(R.id.tournament_name).text = "${tournament.tournamentName} (${tournament.matchType.getValue()})"
        itemView.findViewById<TextView>(R.id.tournament_date).text = tournament.date
    }
}