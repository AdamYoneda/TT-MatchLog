package com.example.ttmatchlog.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    fun getInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://example.com/") // Not actually used for dynamic URLs
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}