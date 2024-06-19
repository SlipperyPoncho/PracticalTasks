package com.artem.android.authfeature

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.artem.android.authfeature.viewmodel.AuthViewModel

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun AuthScreen(
    authViewModel: AuthViewModel,
    onLoginClick: () -> Unit) {
    val primaryColor = Color(android.graphics.Color.parseColor("#66a636"))
    var email by remember { mutableStateOf(authViewModel.email) }
    var password by remember { mutableStateOf(authViewModel.password) }
    var showPassword by remember { mutableStateOf(authViewModel.showPassword) }
    var isLoginButtonEnabled by remember { mutableStateOf(authViewModel.isLoginButtonEnabled) }
    val visibilityIcon: ImageVector =
        if (showPassword) Icons.Filled.VisibilityOff
        else Icons.Filled.Visibility

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        MaterialTheme(
            colors = MaterialTheme.colors.copy(
                primary = primaryColor
            )
        ) {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = com.artem.android.core.R.string.auth_tv_text),
                        fontFamily = FontFamily(
                            Font(com.artem.android.core.R.font.officina_sans_extra_bold)
                        ),
                        fontSize = 21.sp,
                        color = Color.White,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        textAlign = TextAlign.Center,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { /* Navigate back */ }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(
                                id = com.artem.android.core.R.string.app_name
                            ),
                            tint = Color.White
                        )
                    }
                },
                backgroundColor = MaterialTheme.colors.primary,
                elevation = 0.dp // Remove shadow
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Social Login Text
        Text(
            text = stringResource(id = com.artem.android.core.R.string.auth_tv_social_text),
            textAlign = TextAlign.Center,
            color = Color.Black.copy(alpha = 0.7f),
            fontSize = 14.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 55.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Social Login Icons (Layout with constraints might be needed for precise placement)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 80.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Image(
                painter = painterResource(id = com.artem.android.core.R.drawable.vk),
                contentDescription = stringResource(id = com.artem.android.core.R.string.app_name),
                modifier = Modifier.size(48.dp)
            )
            Image(
                painter = painterResource(id = com.artem.android.core.R.drawable.fb),
                contentDescription = stringResource(id = com.artem.android.core.R.string.app_name),
                modifier = Modifier.size(48.dp)
            )
            Image(
                painter = painterResource(id = com.artem.android.core.R.drawable.ok),
                contentDescription = stringResource(id = com.artem.android.core.R.string.app_name),
                modifier = Modifier.size(48.dp)
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        // "Or sign in with email" text
        Text(
            text = stringResource(id = com.artem.android.core.R.string.auth_tv_3_text),
            textAlign = TextAlign.Center,
            color = Color.Black.copy(alpha = 0.7f),
            fontSize = 14.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 54.dp)
        )
        Text(
            text = stringResource(id = com.artem.android.core.R.string.auth_Email_hint),
            color = Color.Black.copy(alpha = 0.38f),
            fontSize = 12.sp,
            modifier = Modifier.padding(top = 20.dp, start = 25.dp)
        )
        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                isLoginButtonEnabled = it.text.length >= 5
            },
            label = {
                Text(
                    text = stringResource(id = com.artem.android.core.R.string.auth_email_et_text)
                ) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = Color.Black.copy(alpha = 0.38f),
                placeholderColor = Color.Black.copy(alpha = 0.38f)
            )
        )
        Text(
            text = stringResource(id = com.artem.android.core.R.string.auth_pass_hint_text),
            color = Color.Black.copy(alpha = 0.38f),
            fontSize = 12.sp,
            modifier = Modifier.padding(top = 20.dp, start = 25.dp)
        )
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                isLoginButtonEnabled = it.text.length >= 5
            },
            label = {
                Text(
                    text = stringResource(id = com.artem.android.core.R.string.auth_pass_et_text)
                ) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true,
            trailingIcon = {
                IconButton(onClick = { showPassword = !showPassword }) {
                    Icon(imageVector = visibilityIcon, contentDescription = "Show password")
                }
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = Color.Black.copy(alpha = 0.38f),
                placeholderColor = Color.Black.copy(alpha = 0.38f)
            )
        )
        Button(
            onClick = { onLoginClick() },
            enabled = isLoginButtonEnabled,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 40.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = primaryColor
            )
        ) {
            Text(
                text = stringResource(id = com.artem.android.core.R.string.auth_login_btn_text),
                color = Color.White,
                fontSize = 16.sp
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(id = com.artem.android.core.R.string.auth_forgot_pass_text),
                color = primaryColor,
                fontSize = 14.sp
            )
            Text(
                text = stringResource(id = com.artem.android.core.R.string.auth_register_tv_text),
                color = primaryColor,
                fontSize = 14.sp
            )
        }
    }
}

//@RequiresApi(Build.VERSION_CODES.Q)
//@Preview
//@Composable
//fun AuthScreenPreview() {
//    AuthScreen(state = AuthState()) {
//
//    }
//}