package com.artem.android.task1.blockkotlinpart2

// Создать интерфейс AuthCallback с методами authSuccess, authFailed
interface AuthCallback {
    fun authSuccess()
    fun authFailed()
}