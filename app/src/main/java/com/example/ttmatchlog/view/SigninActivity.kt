package com.example.ttmatchlog.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ttmatchlog.databinding.ActivitySigninBinding

class SigninActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySigninBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // bindingの初期化
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 登録画面への切り替え
        binding.move.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.signinBtn.setOnClickListener {
            println("------------")
            print(binding.userName.getText().toString().trim())
            print(binding.email.getText().toString().trim())
            print(binding.password.getText().toString().trim())
        }
    }
}