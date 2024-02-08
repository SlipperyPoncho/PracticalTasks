package com.artem.android.task1.blockkotlinpart1

// Создать два класса, реализующих данный интерфейс – Book и Magazine.
// В методе getType для класса Book возвращаем строку с зависимости от количества слов.
// Если количество слов не превышает 1000, то вывести “Flash Fiction”, 7,500 –“Short Story”,
// всё, что выше – “Novel”. Для класса Magazine возвращаем строку “Magazine”.
class Book(override val price: Int,
           override val wordCount: Int) : Publication {

    override fun getType(str: String): String = when (this.wordCount) {
        in 0..1000 -> "Flash Fiction"
        in 1001..7500 -> "Short Story"
        in 7500..Int.MAX_VALUE -> "Novel"
        else -> "No words"
    }

    //У класса Book переопределить метод equals и произвести сравнение сначала по ссылке,
    // затем используя метод equals. Результаты сравнений вывести в лог (note: в MainActivity).
    override fun equals(other: Any?): Boolean {
        return if (this === other) {
            this == other
        } else false
    }

    override fun hashCode(): Int {
        var result = price
        result = 31 * result + wordCount
        return result
    }
}