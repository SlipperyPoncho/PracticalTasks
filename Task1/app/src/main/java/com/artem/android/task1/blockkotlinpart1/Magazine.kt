package com.artem.android.task1.blockkotlinpart1

// Создать два класса, реализующих данный интерфейс – Book и Magazine.
// В методе getType для класса Book возвращаем строку с зависимости от количества слов.
// Если количество слов не превышает 1000, то вывести “Flash Fiction”, 7,500 –“Short Story”,
// всё, что выше – “Novel”. Для класса Magazine возвращаем строку “Magazine”.
class Magazine(override val price: Int,
               override val wordCount: Int) : Publication {

    override fun getType(str: String): String = "Magazine"
}