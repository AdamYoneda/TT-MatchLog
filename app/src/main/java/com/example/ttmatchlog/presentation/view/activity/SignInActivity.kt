package com.example.ttmatchlog.presentation.view.activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Observer
import com.example.myapplication_2.viewmodel.SignInViewModel
import com.example.ttmatchlog.R
import com.example.ttmatchlog.databinding.ActivitySigninBinding
import com.example.ttmatchlog.utils.UserManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySigninBinding
    private val signupViewModel: SignInViewModel by viewModels()
    private var buttonIsClicked = false
    private var selectedImageUri: Uri? = null

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

            if (selectedImageUri != null) {
                // Upload image and proceed to sign-up
                signupViewModel.uploadImageToFirebase(selectedImageUri!!) { imageUrl ->
                    if (imageUrl != null) {
                        signupViewModel.signup(userName, email, password, imageUrl)
                    } else {
                        Toast.makeText(this, "画像のアップロードに失敗しました", Toast.LENGTH_SHORT).show()
                        buttonIsClicked = false
                    }
                }
            } else {
                Toast.makeText(this, "アイコン画像を選択してください", Toast.LENGTH_SHORT).show()
            }
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
                selectedImageUri = it.data?.data
                binding.userIconImageView.setImageURI(selectedImageUri)
            }
        }
}