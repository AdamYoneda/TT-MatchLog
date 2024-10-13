package com.example.ttmatchlog.presentation.view.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.myapplication_2.viewmodel.LoginViewModel
import com.example.ttmatchlog.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()
    private var buttonIsClicked = false

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

        // ユーザーがすでにログインしている場合、MainActivity に移動
        // authでチェックして、ユーザー情報を取得し、結果を監視する
        loginViewModel.checkUserLoggedIn()
        loginViewModel.loginResult.observe(this, Observer { isLoggedIn ->
            if (isLoggedIn) {
                moveToMatchRecordActivity()
            }
        })

        // ログインボタンのクリックイベント
        binding.loginBtn.setOnClickListener {
            buttonIsClicked = true

            val email = binding.email.text.toString().trim()
            val password = binding.password.text.toString().trim()

            // ViewModel にログイン処理を依頼
            loginViewModel.login(email, password)
        }

        // ログイン結果を監視
        loginViewModel.loginResult.observe(this, Observer { isSuccess ->
            if (isSuccess) {
                moveToMatchRecordActivity()
            } else if (buttonIsClicked) {
                Toast.makeText(this, "ログインに失敗しました。", Toast.LENGTH_SHORT).show()
                buttonIsClicked = false
            }
        })
    }

    private fun moveToMatchRecordActivity() {
        val intent = Intent(this, MatchRecordActivity::class.java)
        startActivity(intent)
    }
}