package com.example.ttmatchlog.presentation.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ttmatchlog.R
import com.example.ttmatchlog.data.model.Match
import com.example.ttmatchlog.data.model.Tournament
import com.example.ttmatchlog.presentation.view.viewholder.MatchViewHolder
import com.example.ttmatchlog.presentation.view.viewholder.TournamentViewHolder

class MatchRecordAdapter(private val tournaments: List<Tournament>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_TOURNAMENT = 0
        private const val VIEW_TYPE_MATCH = 1
    }

    override fun getItemViewType(position: Int): Int {
        // 位置に応じてTournamentかMatchかを判断
        if (isTournamentPosition(position)) {
            return VIEW_TYPE_TOURNAMENT
        } else {
            return VIEW_TYPE_MATCH
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_TOURNAMENT -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.tournament_item, parent, false)
                return TournamentViewHolder(view)
            }
            VIEW_TYPE_MATCH -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.match_item, parent, false)
                return MatchViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemCount(): Int = getTotalItemCount()

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is TournamentViewHolder) {
            // Tournamentデータをバインド
            holder.bind(tournaments[getTournamentIndex(position)])
        } else if (holder is MatchViewHolder) {
            // Matchデータをバインド
            holder.bind(getMatchAtPosition(position))
        }
    }

    /* サポート関数を追加（位置判定やデータ取得など）*/
    private fun isTournamentPosition(position: Int): Boolean {
        // 各Tournamentは最初のアイテムなので、positionが大会の開始位置かどうかをチェック
        var itemCount = 0
        for (tournament in tournaments) {
            if (position == itemCount) {
                return true // 大会の開始位置ならtrueを返す
            }
            // 各大会のセクションは、1つの大会 + その大会の試合数分のアイテムを持つ
            itemCount += 1 + tournament.matches.size
        }
        return false
    }

    // positionに基づいて対応するTournamentのインデックスを返す
    private fun getTournamentIndex(position: Int): Int {
        var itemCount = 0
        for (i in tournaments.indices) {
            if (position == itemCount) {
                return i // 該当のTournamentのインデックスを返す
            }
            // Tournamentとその試合数分のアイテム数をカウント
            itemCount += 1 + tournaments[i].matches.size
        }
        throw IllegalArgumentException("Invalid position for tournament")
    }

    // positionに基づいて対応するMatchを返す
    private fun getMatchAtPosition(position: Int): Match {
        var itemCount = 0
        for (tournament in tournaments) {
            itemCount += 1 // Tournament自体をスキップ
            for (match in tournament.matches) {
                if (position == itemCount) {
                    return match // 該当するMatchを返す
                }
                itemCount += 1 // 各Matchのためにカウントを進める
            }
        }
        throw IllegalArgumentException("Invalid position for match")
    }

    // Tournament + Matchの合計数を計算
    private fun getTotalItemCount(): Int {
        var totalCount = 0
        for (tournament in tournaments) {
            // 各Tournamentセクション + その中の試合数を加算
            totalCount += 1 + tournament.matches.size
        }
        return totalCount
    }
}