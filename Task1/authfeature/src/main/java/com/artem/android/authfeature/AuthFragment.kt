package com.artem.android.authfeature

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.artem.android.authfeature.viewmodel.AuthViewModel

class AuthFragment: Fragment() {

    fun interface LoginCallback { fun onLoginClicked() }

    private var callbacks: LoginCallback? = null
    private lateinit var authComponent: AuthComponent
    private lateinit var composeView: ComposeView
    private val authViewModel: AuthViewModel by viewModels() {
        authComponent.authViewModelFactory()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        authComponent = (requireActivity().applicationContext as AuthComponentProvider)
            .provideAuthComponent()
        authComponent.inject(this)
        callbacks = context as LoginCallback?
    }

    @SuppressLint("CheckResult")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).also {
            composeView = it
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        composeView.setContent {
            AuthScreen(
                authViewModel = authViewModel,
                onLoginClick = {
                    callbacks?.onLoginClicked()
                }
            )
        }
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