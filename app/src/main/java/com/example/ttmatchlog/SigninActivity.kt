package com.example.ttmatchlog

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ttmatchlog.databinding.ActivitySigninBinding

class SigninActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySigninBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // bindingの初期化
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 登録画面へ遷移
        binding.move.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}