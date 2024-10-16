package com.example.ttmatchlog.data.repository

import android.content.Context
import android.net.Uri
import android.util.Log
import com.example.ttmatchlog.data.api.ImageApi
import com.example.ttmatchlog.data.api.RetrofitInstance
import okhttp3.ResponseBody
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class ImageRepository(private val context: Context) {

    private val imageApi = RetrofitInstance.getInstance().create(ImageApi::class.java)

    fun downloadImageToUri(urlString: String, onResult: (Uri?) -> Unit) {
        imageApi.downloadImage(urlString).enqueue(object : retrofit2.Callback<ResponseBody> {
            override fun onResponse(
                call: retrofit2.Call<ResponseBody>,
                response: retrofit2.Response<ResponseBody>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    response.body()?.let { body ->
                        val uri = saveImageToInternalStorage(body)
                        onResult(uri)
                    } ?: onResult(null)
                } else {
                    Log.e("ImageRepository", "Failed to download image: ${response.errorBody()}")
                    onResult(null)
                }
            }

            override fun onFailure(call: retrofit2.Call<ResponseBody>, t: Throwable) {
                Log.e("ImageRepository", "Download failed: ${t.message}")
                onResult(null)
            }
        })
    }

    private fun saveImageToInternalStorage(body: ResponseBody): Uri? {
        return try {
            val file = File(context.filesDir, "downloaded_image.jpg")
            val inputStream: InputStream = body.byteStream()
            val outputStream = FileOutputStream(file)

            inputStream.use { input ->
                outputStream.use { output ->
                    input.copyTo(output)
                }
            }
            Uri.fromFile(file)
        } catch (e: Exception) {
            Log.e("ImageRepository", "Error saving image: ${e.message}")
            null
        }
    }
}
