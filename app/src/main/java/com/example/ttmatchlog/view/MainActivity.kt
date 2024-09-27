package com.example.ttmatchlog.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.ttmatchlog.databinding.ActivityMainBinding
import com.example.ttmatchlog.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // bindingの初期化
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // signoutBtnのクリックイベント
        binding.signoutBtn.setOnClickListener {
            mainViewModel.signout()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}