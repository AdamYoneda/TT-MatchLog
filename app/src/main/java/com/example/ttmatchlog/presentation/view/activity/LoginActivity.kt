package com.example.ttmatchlog.presentation.view.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication_2.viewmodel.LoginViewModel

class LoginActivity : ComponentActivity() {
    private val loginViewModel: LoginViewModel by viewModels()
    private var buttonIsClicked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginScreen(
                onLoginClick = { email, password ->
                    buttonIsClicked = true
                    loginViewModel.login(email, password)
                },
                onMoveToSignUp = { moveToSignInActivity() }
            )

            // ユーザーがログイン済みかチェック
            loginViewModel.checkUserLoggedIn()

            // ログイン結果を監視
            loginViewModel.loginResult.observe(this) { isSuccess ->
                if (isSuccess) {
                    moveToMatchRecordActivity()
                } else if (buttonIsClicked) {
                    Toast.makeText(this, "ログインに失敗しました。", Toast.LENGTH_SHORT).show()
                    buttonIsClicked = false
                }
            }
        }
    }

    private fun moveToSignInActivity() {
        startActivity(Intent(this, SignInActivity::class.java))
    }

    private fun moveToMatchRecordActivity() {
        startActivity(Intent(this, MatchRecordActivity::class.java))
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    LoginScreen(
        onLoginClick = { _, _ -> /* No action */ },
        onMoveToSignUp = { /* No action */ }
    )
}

@Composable
fun LoginScreen(
    onLoginClick: (String, String) -> Unit,
    onMoveToSignUp: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email", color = Color.DarkGray) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent),
            singleLine = true,
            textStyle = LocalTextStyle.current.copy(fontSize = 20.sp, color = Color.DarkGray)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password", color = Color.DarkGray) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent),
            singleLine = true,
            textStyle = LocalTextStyle.current.copy(fontSize = 20.sp, color = Color.DarkGray)
        )

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = { onLoginClick(email, password) },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF009688))
        ) {
            Text("ログイン", fontSize = 20.sp, color = Color.White)
        }

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "まだアカウントをお持ちでない方はこちら",
            color = Color(0xFF03A9F4),
            modifier = Modifier.clickable { onMoveToSignUp() }
        )
    }
}
