package com.example.myapplication_2.viewmodel

import android.net.Uri
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
import com.google.firebase.storage.ktx.storage
import java.util.UUID

class SignInViewModel : ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth
    private val db = Firebase.firestore
    private val storage = Firebase.storage

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

    fun uploadImageToFirebase(imageUri: Uri, onResult: (String?) -> Unit) {
        val storageRef = storage.reference.child("user_icons/${UUID.randomUUID()}.jpg")

        // Log the upload attempt
        Log.d("SignInVM-Storage", "Starting upload for URI: $imageUri")
        storageRef.putFile(imageUri)
            .addOnSuccessListener {
                Log.d("SignInVM-Storage", "Upload successful. Retrieving download URL.")
                storageRef.downloadUrl.addOnSuccessListener { uri ->
                    Log.d("SignInVM-Storage", "Download URL retrieved: $uri")
                    onResult(uri.toString())
                }.addOnFailureListener { e ->
                    Log.e("SignInVM-Storage", "Failed to get download URL", e)
                    onResult(null)
                }
            }
            .addOnFailureListener { e ->
                Log.e("SignInVM-Storage", "Upload failed", e)
                onResult(null)
            }
    }

    // A. 新規ユーザー登録
    fun signup(userName: String, email: String, password: String, imageUrl: String) {
        Log.d("SignInVM-Signup", "Starting signup process")
        // A-0. email または password が空の場合はすぐに false を返す
        if (userName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Log.e("SignInVM-Signup", "User input is empty")
            _signupResult.value = false
            return
        }
        // A-1 Authを使った新規登録
        registerUser(email, password) { userId ->
            if (userId != null) {
                Log.d("SignInVM-Signup", "User registered with ID: $userId")
                // A-2 新規登録が成功したユーザーをFirestoreへ登録
                saveUserToFirestore(userId, userName, email, imageUrl) { isSaved ->
                    if (isSaved) {
                        Log.d("SignInVM-Signup", "User saved to Firestore")
                        // A-3 Firestoreからユーザー情報を取得
                        fetchUserInfo(userId) { _ ->
                            _signupResult.value = true
                        }
                    } else {
                        Log.e("SignInVM-Signup", "Failed to save user to Firestore")
                        _signupResult.value = false // Firestoreへの保存失敗
                    }
                }
            } else {
                Log.e("SignInVM-Signup", "User registration failed")
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
                    Log.d("SignInVM-Firestore", "Failed to fetch user data")
                }
            }
            .addOnFailureListener {
                Log.d("SignInVM-Firestore", "Failed to fetch user data")
            }
    }
}