package com.example.myapplication_2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginViewModel: ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth

    // ログイン結果を管理する LiveData
    private val _loginResult = MutableLiveData<Boolean>()
    val loginResult: LiveData<Boolean> = _loginResult

    // ログイン処理
    fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                _loginResult.value = task.isSuccessful
            }
    }

    // 現在のユーザーのチェック
    fun isUserLoggedIn(): Boolean {
        return auth.currentUser != null
    }
}