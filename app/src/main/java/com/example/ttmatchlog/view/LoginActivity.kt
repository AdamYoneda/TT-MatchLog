package com.example.ttmatchlog.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ttmatchlog.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // bindingの初期化
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 登録画面へ遷移
        binding.move.setOnClickListener {
            val intent = Intent(this, SigninActivity::class.java)
            startActivity(intent)
        }

        binding.loginBtn.setOnClickListener {
            println("------------")
            print(binding.email.getText().toString().trim())
            print(binding.password.getText().toString().trim())
        }
    }
}