package com.example.ttmatchlog.presentation.view.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
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
    private var buttonIsClicked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        setupSplashScreen()
        super.onCreate(savedInstanceState)

        // bindingの初期化
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // アイコン画像を登録
        binding.userIconImageView.setOnClickListener {
            val pickImg = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI).apply {
                type = "image/*"
            }
            changeImage.launch(pickImg)
        }

        // ログイン画面への切り替え
        binding.move.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        // ユーザーがすでにログインしている場合、MainActivity に移動
        // authでチェックして、ユーザー情報を取得し、結果を監視する
        signupViewModel.checkUserLoggedIn()
        signupViewModel.signupResult.observe(this, Observer { isLoggedIn ->
            if (isLoggedIn) {
                moveToMatchRecordActivity()
            }
        })

        // 登録ボタンのクリックイベント
        binding.signinBtn.setOnClickListener {
            buttonIsClicked = true

            val userName = binding.userName.text.toString()
            val email = binding.email.text.toString().trim()
            val password = binding.password.text.toString().trim()

            // ViewModel に新規登録処理を依頼
            signupViewModel.signup(userName, email, password)
        }

        // 新規ユーザー登録の結果を監視
        signupViewModel.signupResult.observe(this, Observer { isSuccess ->
            if (isSuccess) {
                moveToMatchRecordActivity()
            } else if (buttonIsClicked) {
                Toast.makeText(this, "登録に失敗しました。", Toast.LENGTH_SHORT).show()
                buttonIsClicked = false
            }
        })
    }

    private fun moveToMatchRecordActivity() {
        val intent = Intent(this, MatchRecordActivity::class.java)
        startActivity(intent)
    }

    private fun setupSplashScreen() {
        installSplashScreen()
        setTheme(R.style.Theme_TTMatchLog)
    }

    private val changeImage =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                val data = it.data
                val imgUri = data?.data
                findViewById<ImageView>(R.id.userIconImageView).setImageURI(imgUri)
            }
        }
}