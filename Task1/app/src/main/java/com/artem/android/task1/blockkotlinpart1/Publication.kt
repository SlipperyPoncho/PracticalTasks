package com.artem.android.task1.blockkotlinpart1

// Необходимо создать интерфейс Publication, у которого должно быть свойства – price и wordCount,
// а также метод getType, возвращающий строку.
interface Publication {
    val price: Int
    val wordCount: Int

    fun getType(str: String): String
}