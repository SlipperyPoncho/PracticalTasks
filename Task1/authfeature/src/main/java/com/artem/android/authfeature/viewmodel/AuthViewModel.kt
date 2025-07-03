package com.artem.android.authfeature.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class AuthViewModel @Inject constructor(): ViewModel() {

    private var _email = TextFieldValue("")
    var email get() = _email
        set(value) {
            _email = value
        }

    private var _password = TextFieldValue("")
    var password get() = _password
        set(value) {
            _password = value
        }

    private var _showPassword = false
    var showPassword get() = _showPassword
        set(value) {
            _showPassword = value
        }

    private var _loginButton = false
    var isLoginButtonEnabled get() = _loginButton
        set(value) {
            _loginButton = value
        }
}
