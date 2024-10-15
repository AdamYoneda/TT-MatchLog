package com.example.myapplication_2.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ttmatchlog.utils.UserManager
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
    fun checkUserLoggedIn() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            fetchUserInfo(currentUser.uid) { fetchedResult ->
                _signupResult.value = fetchedResult
            }
        } else {
            _signupResult.value = false
        }
    }

    // A. 新規ユーザー登録
    fun signup(userName: String, email: String, password: String) {
        // A-0. email または password が空の場合はすぐに false を返す
        if (userName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            _signupResult.value = false
            return
        }
        // A-1 Authを使った新規登録
        registerUser(email, password) { userId ->
            if (userId != null) {
                // A-2 新規登録が成功したユーザーをFirestoreへ登録
                saveUserToFirestore(userId, userName, email) { isSaved ->
                    if (isSaved) {
                        // A-3 Firestoreからユーザー情報を取得
                        fetchUserInfo(userId) { _ ->
                            _signupResult.value = true
                        }
                    } else {
                        _signupResult.value = false // Firestoreへの保存失敗
                    }
                }
            } else {
                _signupResult.value = false // ユーザー登録失敗
            }
        }
    }

    // Authを使った新規登録
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

    // 新規登録が成功したユーザーをFirestoreへアップロード
    private fun saveUserToFirestore(
        userId: String,
        userName: String,
        email: String,
        imageUrl: String,
        onResult: (Boolean) -> Unit
    ) {
        val user = hashMapOf(
            "user_id" to userId,
            "user_name" to userName,
            "email" to email,
            "image_url" to imageUrl,
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

    // Firestoreへアップロードしたユーザー情報を取得
    private fun fetchUserInfo(userId: String, onResult: (Boolean) -> Unit) {
        db.collection("users").document(userId).get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    // Firestoreから取得したデータをUserモデルに変換
                    UserManager.setUser(
                        userId = document.getString("user_id") ?: "",
                        userName = document.getString("user_name") ?: "",
                        email = document.getString("email") ?: "",
                        imageUrl = document.getString("image_url") ?: "",
                        joinedDate = document.getTimestamp("joined_date")
                    )
                    onResult(true)
                } else {
                    // 取得に失敗した場合
                    Log.d("Firestore", "Failed to fetch user data")
                }
            }
            .addOnFailureListener {
                Log.d("Firestore", "Failed to fetch user data")
            }
    }
}