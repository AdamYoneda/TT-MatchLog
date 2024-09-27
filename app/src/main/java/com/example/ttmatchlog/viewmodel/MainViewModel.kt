package com.example.ttmatchlog.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainViewModel: ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth

    fun signout() {
        auth.signOut()
    }
}