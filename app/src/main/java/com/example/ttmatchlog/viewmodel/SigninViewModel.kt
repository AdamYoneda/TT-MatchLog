package com.example.myapplication_2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SigninViewModel : ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth

    // 新規ユーザー登録結果を管理する LiveData
    private val _signupResult = MutableLiveData<Boolean>()
    val signupResult: LiveData<Boolean> = _signupResult

    // 新規ユーザー登録
    fun signup(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                _signupResult.value = task.isSuccessful
            }
    }

    // 現在のユーザーのチェック
    fun isUserLoggedIn(): Boolean {
        return  auth.currentUser != null
    }
}