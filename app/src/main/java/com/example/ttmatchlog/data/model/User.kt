package com.example.ttmatchlog.data.model

import com.google.firebase.Timestamp

data class User(
    val userId: String = "",
    val userName: String = "",
    val email: String = "",
    val imageUrl: String = "",
    val joinedDate: Timestamp? = null
)