package com.artem.android.authfeature

import dagger.Subcomponent

@Subcomponent
interface AuthComponent {
    fun inject(fragment: AuthFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): AuthComponent
    }
}