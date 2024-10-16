package com.example.ttmatchlog.presentation.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.ttmatchlog.R
import com.example.ttmatchlog.data.model.Match

class MatchInputAdapter(context: Context, private val matches: MutableList<Match>) : ArrayAdapter<Match>(context, 0, matches) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // アイテムのデータを取得
        val match = getItem(position)

        // 既存のビューを再利用するか、新規作成
        val view = LayoutInflater.from(context).inflate(R.layout.match_item, parent, false)

        // カスタムレイアウト内のTextViewを取得
        val opponentNameTextView = view.findViewById<TextView>(R.id.opponent_name)
        val scoreTextView = view.findViewById<TextView>(R.id.score)

        // データをビューにセット
        opponentNameTextView.text = "${match?.roundNumber}回戦 ${match?.opponentName}"
        scoreTextView.text = "${match?.playerScore} - ${match?.opponentScore}"

        return view
    }
}