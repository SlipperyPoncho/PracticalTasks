package com.artem.android.authfeature

interface AuthComponentProvider {
    fun provideAuthComponent(): AuthComponent
}