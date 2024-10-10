package com.example.ttmatchlog.presentation.view.activity

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ttmatchlog.R
import com.example.ttmatchlog.data.repository.TournamentRepository
import com.example.ttmatchlog.presentation.view.adapter.MatchRecordAdapter
import com.example.ttmatchlog.presentation.viewmodel.MatchRecordViewModel
import com.example.ttmatchlog.presentation.viewmodel.MatchRecordViewModelFactory
import com.example.ttmatchlog.utils.UserManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView

class MatchRecordActivity : AppCompatActivity() {

    private lateinit var viewModel: MatchRecordViewModel
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_record)

        // ViewModelの初期化
        val repository = TournamentRepository()
        viewModel = ViewModelProvider(this, MatchRecordViewModelFactory(repository))
            .get(MatchRecordViewModel::class.java)

        // recyclerViewの初期化
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // ViewModelからデータを取得し、RecyclerViewに設定
        viewModel.tournaments.observe(this, Observer { tournaments ->
            recyclerView.adapter = MatchRecordAdapter(tournaments)
        })

        // データを読み込む
        viewModel.loadTournaments()

        setupDrawer() // ナビゲーションドロワーの設定

        // Access the FloatingActionButton
        val fab: FloatingActionButton = findViewById(R.id.fab)

        // Set a click listener on the FAB
        fab.setOnClickListener {
            // Navigate to match input screen, passing tournament data
            val intent = Intent(this, TournamentInputActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupDrawer() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.open, R.string.close
        )

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val navView: NavigationView = findViewById(R.id.nav_view)

        // ヘッダービューを取得
        val headerView = navView.getHeaderView(0) // ヘッダーレイアウトの最初のビューを取得
        val profileUserName: TextView = headerView.findViewById(R.id.profile_user_name)
        val userName = UserManager.getUser()?.userName
        profileUserName.text = userName

        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_edit_profile -> {
                    // プロフィール編集処理
                }
                R.id.nav_forms -> {
                    // お問い合わせ処理
                }
                R.id.nav_logout -> {
                    // ログアウト処理
                }
                R.id.nav_delete_account -> {
                    // アカウント削除処理
                }
            }
            drawerLayout.closeDrawers()
            true
        }
    }
}