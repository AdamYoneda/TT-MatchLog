package com.example.ttmatchlog.data.api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface ImageApi {
    // Dynamic URL to download the image
    @GET
    fun downloadImage(@Url fileUrl: String): Call<ResponseBody>
}