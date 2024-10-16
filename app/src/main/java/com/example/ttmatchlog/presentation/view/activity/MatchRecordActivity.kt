package com.example.ttmatchlog.presentation.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ttmatchlog.R
import com.example.ttmatchlog.data.model.User
import com.example.ttmatchlog.data.repository.ImageRepository
import com.example.ttmatchlog.data.repository.TournamentRepository
import com.example.ttmatchlog.presentation.view.adapter.MatchRecordAdapter
import com.example.ttmatchlog.presentation.viewmodel.MatchRecordViewModel
import com.example.ttmatchlog.presentation.viewmodel.MatchRecordViewModelFactory
import com.example.ttmatchlog.utils.UserManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView

class MatchRecordActivity : AppCompatActivity() {

    private val viewModel: MatchRecordViewModel by viewModels {
        MatchRecordViewModelFactory(TournamentRepository())
    }
    private lateinit var recyclerView: RecyclerView
    private lateinit var imageRepository: ImageRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (viewModel.checkUserIsLogout()) {
            navigateToLogin()
        }
        setContentView(R.layout.activity_match_record)

        // データを読み込む
        val user = UserManager.getUser() ?: return
        viewModel.loadTournaments(user.userId)
        imageRepository = ImageRepository(this)
        setupUI(user)
        observeViewModel()
        viewModel.downloadImage(user.imageUrl, imageRepository)
    }

    private fun setupUI(user: User) {
        // recyclerViewの初期化
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        setupDrawer(user)
        setupFab()
    }

    private fun setupFab() {
        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this, TournamentInputActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupDrawer(user: User) {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.open, R.string.close
        )

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val navView: NavigationView = findViewById(R.id.nav_view)
        setupNavigationView(user, navView, drawerLayout)
    }

    private fun setupNavigationView(user: User, navView: NavigationView, drawerLayout: DrawerLayout) {
        val headerView = navView.getHeaderView(0)
        val profileUserName: TextView = headerView.findViewById(R.id.profile_user_name)
        val profileUserIcon: ImageView = headerView.findViewById(R.id.profile_user_icon)

        profileUserName.text = user.userName

        viewModel.userIconUri.observe(this) { uri ->
            profileUserIcon.setImageURI(uri)
        }

        navView.setNavigationItemSelectedListener { menuItem ->
            handleNavigation(menuItem, drawerLayout)
        }
    }

    private fun handleNavigation(menuItem: MenuItem, drawerLayout: DrawerLayout): Boolean {
        when (menuItem.itemId) {
            R.id.nav_edit_profile -> { /* Profile edit logic */ }
            R.id.nav_forms -> { /* Inquiry logic */ }
            R.id.nav_logout -> {
                viewModel.signout()
                navigateToLogin()
            }
            R.id.nav_delete_account -> { /* Account deletion logic */ }
        }
        drawerLayout.closeDrawers()
        return true
    }

    // ユーザーがログアウト状態ならLoginActivityに戻す
    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    // ViewModelからデータを取得し、RecyclerViewに設定
    private fun observeViewModel() {
        viewModel.tournaments.observe(this, Observer { tournaments ->
            recyclerView.adapter = MatchRecordAdapter(tournaments)
        })
    }
}