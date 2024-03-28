package com.artem.android.task1

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import com.jakewharton.rxbinding4.widget.textChangeEvents
import io.reactivex.rxjava3.kotlin.subscribeBy

class AuthFragment: Fragment() {

    fun interface LoginCallback { fun onLoginClicked() }

    private var callbacks: LoginCallback? = null
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var passwordVisible: ImageButton
    private lateinit var loginBtn: Button

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as LoginCallback?
    }

    @SuppressLint("CheckResult")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_auth, container, false)
        emailEditText = view.findViewById(R.id.auth_email_et)
        passwordEditText = view.findViewById(R.id.auth_pass_et)
        passwordVisible = view.findViewById(R.id.auth_pass_visible_ib)
        loginBtn = view.findViewById(R.id.auth_login_btn)
        passwordEditText.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD

        emailEditText.textChangeEvents().subscribeBy(
            onNext = {
                loginBtn.isEnabled = it.text.length > 5 && passwordEditText.text.length > 5
            }
        )
        passwordEditText.textChangeEvents().subscribeBy(
            onNext = {
                loginBtn.isEnabled = it.text.length > 5 && emailEditText.text.length > 5
            }
        )

        passwordVisible.setOnClickListener {
            if (passwordEditText.inputType == InputType.TYPE_TEXT_VARIATION_PASSWORD) {
                passwordEditText.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                it.setBackgroundResource(R.drawable.close)
            } else {
                passwordEditText.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
                it.setBackgroundResource(R.drawable.open)
            }
        }

        loginBtn.setOnClickListener {
            callbacks?.onLoginClicked()
        }
        return view
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    companion object {
        fun newInstance(): Fragment {
            return AuthFragment()
        }
    }
}