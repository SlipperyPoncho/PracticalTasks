package com.artem.android.authfeature

import com.artem.android.authfeature.viewmodel.AuthViewModelFactory
import dagger.Subcomponent

@Subcomponent
interface AuthComponent {
    fun inject(fragment: AuthFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): AuthComponent
    }

    fun authViewModelFactory(): AuthViewModelFactory
}