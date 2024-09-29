package com.example.ttmatchlog.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.ttmatchlog.databinding.ActivityMainBinding
import com.example.ttmatchlog.utils.UserManager
import com.example.ttmatchlog.viewmodel.MainViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // bindingの初期化
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Fetch user information from UserManager and display it
        displayUserInfo()

        // signoutBtnのクリックイベント
        binding.signoutBtn.setOnClickListener {
            mainViewModel.signout()

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    // Method to display user info
    private fun displayUserInfo() {
        val user = UserManager.getUser()

        if (user != null) {
            binding.userNameText.text = "User Name: ${user.userName}"
            binding.emailText.text = "Email: ${user.email}"
            binding.joinedDateText.text =
                "Joined Date: ${user.joinedDate?.toDate()?.toString() ?: "N/A"}"
        } else {
            binding.userNameText.text = "User not found"
            binding.emailText.text = ""
            binding.joinedDateText.text = ""
        }
    }
}