package com.example.myapplication_2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SigninViewModel : ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth
    private val db = Firebase.firestore

    // 新規ユーザー登録結果を管理する LiveData
    private val _signupResult = MutableLiveData<Boolean>()
    val signupResult: LiveData<Boolean> = _signupResult

    // 現在のユーザーのチェック
    fun isUserLoggedIn(): Boolean {
        return  auth.currentUser != null
    }

    // A. 新規ユーザー登録
    fun signup(userName: String, email: String, password: String) {
        // A-1 Authを使った新規登録
        registerUser(email, password) { userId ->
            if (userId != null) {
                // A-2 新規登録が成功したユーザーをFirestoreへ登録
                saveUserToFirestore(userId, userName, email) { isSaved ->
                    _signupResult.value = isSaved // Firestore保存結果を反映
                }
            } else {
                _signupResult.value = false // ユーザー登録失敗
            }
        }
    }

    // 1.Authを使った新規登録
    private fun registerUser(email: String, password: String, onResult: (String?) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid
                    onResult(userId)
                } else {
                    onResult(null)
                }
            }
    }

    // 2.新規登録が成功したユーザーをFirestoreへ登録
    private fun saveUserToFirestore(
        userId: String,
        userName: String,
        email: String,
        onResult: (Boolean) -> Unit
    ) {
        val user = hashMapOf(
            "user_id" to userId,
            "user_name" to userName,
            "email" to email,
            "joined_date" to Timestamp.now()
        )
        // Firestoreのusersコレクションにユーザーデータを保存
        db.collection("users").document(userId).set(user)
            .addOnSuccessListener {
                onResult(true)
            }
            .addOnFailureListener {
                onResult(false)
            }
    }
}