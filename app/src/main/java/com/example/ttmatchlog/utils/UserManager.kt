package com.example.ttmatchlog.utils

import com.example.ttmatchlog.data.model.User
import com.google.firebase.Timestamp

object UserManager {
    private var user: User? = null

    fun setUser(userId: String, userName: String, email: String, joinedDate: Timestamp?) {
        user = User(userId, userName, email, joinedDate)
    }

    fun getUser(): User? {
        return user
    }

    fun clearUser() {
        user = null
    }
}