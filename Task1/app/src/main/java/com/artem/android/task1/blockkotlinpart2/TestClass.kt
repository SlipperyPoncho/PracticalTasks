package com.artem.android.task1.blockkotlinpart2

import android.util.Log
import com.artem.android.task1.blockkotlinpart2.action.Action
import com.artem.android.task1.blockkotlinpart2.action.Login
import com.artem.android.task1.blockkotlinpart2.action.Logout
import com.artem.android.task1.blockkotlinpart2.action.Registration

class TestClass {
    fun main() {
        // Создать объект класса User, вывести в лог startTime данного юзера,
        // после вызвать Thread.sleep(1000) и повторно вывести в лог startTime.
        val user = User()
        Log.i(TAG,"${user.startTime}")
        Thread.sleep(1000)
        Log.i(TAG,"${user.startTime}")

        // Создать список пользователей, содержащий в себе один объект класса User.
        // Используя функцию apply, добавить ещё несколько объектов класса User
        // в список пользователей.
        val usersList: List<User> = arrayListOf(User(
            0,
            "artem",
            22,
            Type.FULL)
        ).apply {
            addAll(arrayListOf(
                User(1, "ivan", 18, Type.DEMO),
                User(2, "petr", 17, Type.FULL),
                User(3, "alex", 12, Type.DEMO)
            ))
        }

        // Получить список пользователей, у которых имеется полный доступ
        // (поле type имеет значение FULL).
        val usersFullType: List<User> = usersList.filter { it.type == Type.FULL }
        Log.i(TAG, "$usersFullType")

        // Преобразовать список User в список имен пользователей.
        // Получить первый и последний элементы списка и вывести их в лог.
        val namesList: List<String> = usersList.map { it.name }
        Log.i(TAG, "First name = ${namesList.first()}\nLast name = ${namesList.last()}")

        try {
            usersList[1].isOldEnough()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // Анонимный объект интерфейса AuthCallback.
        // В методах необходимо вывести в лог информацию о статусе авторизации.
        val authCallback = object : AuthCallback {
            override fun authSuccess() {
                Log.i(TAG, "Authorization successful")
            }
            override fun authFailed() {
                Log.i(TAG, "Authorization failed")
            }
        }

        doAction(Login(usersList[1]), authCallback)
    }

    // Создать функцию-расширение класса User, которая проверяет, что юзер старше 18 лет,
    // и в случае успеха выводит в лог, а в случае неуспеха возвращает ошибку.
    private fun User.isOldEnough(): Boolean {
        if (this.age >= 18) {
            Log.i(TAG, "User older 18")
            return true
        } else {
            throw Exception("User under 18")
        }
    }

    // Реализовать inline функцию auth, принимающую в качестве параметра функцию updateCache.
    // Функция updateCache должна выводить в лог информацию об обновлении кэша.
    // Внутри функции auth вызвать метод коллбека authSuccess и переданный updateCache,
    // если проверка возраста пользователя произошла без ошибки.
    // В случае получения ошибки вызвать authFailed.
    private inline fun auth(user: User, authCallback: AuthCallback, updateCache: () -> Unit) {
        if (user.isOldEnough()) {
            authCallback.authSuccess()
            updateCache()
        } else {
            authCallback.authFailed()
        }
    }

    // Реализовать метод doAction, принимающий экземпляр класса Action.
    // В зависимости от переданного действия выводить в лог текст, к примеру “Auth started”.
    // Для действия Login вызывать метод auth.
    private fun doAction(action: Action, authCallback: AuthCallback) {
        when (action) {
            is Login -> {
                auth(action.user, authCallback) { Log.i(TAG, "Updating cache") }
                Log.i(TAG,"Log in")
            }
            is Logout -> Log.i(TAG,"Log out")
            is Registration -> Log.i(TAG,"Registration started")
        }
    }

    companion object { const val TAG = "TestClassKotlinPart2" }
}