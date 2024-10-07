package com.example.ttmatchlog.presentation.view.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Observer
import com.example.myapplication_2.viewmodel.SigninViewModel
import com.example.ttmatchlog.R
import com.example.ttmatchlog.databinding.ActivitySigninBinding

class SigninActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySigninBinding
    private val signupViewModel: SigninViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        setupSplashScreen()

        super.onCreate(savedInstanceState)

        // bindingの初期化
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 登録画面への切り替え
        binding.move.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        // ユーザーがすでにログインしている場合、MainActivity に移動
        if (signupViewModel.isUserLoggedIn()) {
            moveToMainActivity()
        }

        // 登録ボタンのクリックイベント
        binding.signinBtn.setOnClickListener {
            val userName = binding.userName.text.toString()
            val email = binding.email.text.toString().trim()
            val password = binding.password.text.toString().trim()

            // ViewModel に新規登録処理を依頼
            signupViewModel.signup(userName, email, password)
        }

        // 新規ユーザー登録の結果を監視
        signupViewModel.signupResult.observe(this, Observer { isSuccess ->
            if (isSuccess) {
                moveToMainActivity()
            } else {
                Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun moveToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun setupSplashScreen() {
        installSplashScreen()
        setTheme(R.style.Theme_TTMatchLog)
    }
}