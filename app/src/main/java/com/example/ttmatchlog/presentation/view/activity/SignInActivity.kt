package com.example.ttmatchlog.presentation.view.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import coil.compose.rememberAsyncImagePainter
import com.example.myapplication_2.viewmodel.SignInViewModel
import com.example.ttmatchlog.R

class SignInActivity : ComponentActivity() {
    private val signupViewModel: SignInViewModel by viewModels()
    private var selectedImageUri by mutableStateOf<Uri?>(null)

    private val changeImage = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            selectedImageUri = result.data?.data
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setupSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            SignInScreen(
                selectedImageUri = selectedImageUri,
                onImageSelect = { pickImage() },
                onSignUpClick = { userName, email, password ->
                    if (selectedImageUri != null) {
                        signupViewModel.uploadImageToFirebase(selectedImageUri!!) { imageUrl ->
                            if (imageUrl != null) {
                                signupViewModel.signup(userName, email, password, imageUrl)
                            } else {
                                showToast("画像のアップロードに失敗しました")
                            }
                        }
                    } else {
                        showToast("アイコン画像を選択してください")
                    }
                },
                onLoginClick = { moveToLogin() }
            )

            signupViewModel.signupResult.observe(this) { isSuccess ->
                if (isSuccess) {
                    moveToMatchRecordActivity()
                } else {
                    showToast("登録に失敗しました。")
                }
            }
        }
    }

    private fun pickImage() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI).apply {
            type = "image/*"
        }
        changeImage.launch(intent)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun moveToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
    }

    private fun moveToMatchRecordActivity() {
        startActivity(Intent(this, MatchRecordActivity::class.java))
    }

    private fun setupSplashScreen() {
        installSplashScreen()
        setTheme(R.style.Theme_TTMatchLog)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSignInScreen() {
    SignInScreen(
        selectedImageUri = null,
        onImageSelect = { /* 何もしない */ },
        onSignUpClick = { _, _, _ -> /* 何もしない */ },
        onLoginClick = { /* 何もしない */ }
    )
}

@Composable
fun SignInScreen(
    selectedImageUri: Uri?,
    onImageSelect: () -> Unit,
    onSignUpClick: (String, String, String) -> Unit,
    onLoginClick: () -> Unit
) {
    var userName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (selectedImageUri != null) {
            Image(
                painter = rememberAsyncImagePainter(selectedImageUri),
                contentDescription = "User Icon",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(160.dp)
                    .clip(CircleShape)
                    .clickable { onImageSelect() }
            )
        } else {
            Image(
                painter = painterResource(R.drawable.baseline_account_circle_24),
                contentDescription = "Default Icon",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(160.dp)
                    .clip(CircleShape)
                    .clickable { onImageSelect() }
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = userName,
            onValueChange = { userName = it },
            label = { Text("User name") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = { onSignUpClick(userName, email, password) },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF009688))
        ) {
            Text("アカウントを作成", fontSize = 20.sp, color = Color.White)
        }

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "すでにアカウントをお持ちの方はこちら",
            color = Color(0xFF03A9F4),
            modifier = Modifier.clickable { onLoginClick() }
        )
    }
}