package com.artem.android.task1.blockkotlinpart1

import android.util.Log

class TestClass {
    // Создать два объекта класса Book и один объект Magazine.
    val book1: Book = Book(150, 2000)
    val book2: Book = Book(200, 8000)
    val magazine: Magazine = Magazine(100, 50)


    // Создать две переменных класса Book, в которых могут находиться null значения.
    // Присвоить одной null, а второй любое notnull значение.
    val book3: Book? = null
    val book4: Book? = Book(300, 500)

    // Создать переменную sum и присвоить ей лямбда-выражение, которое будет складывать два
    // переданных ей числа и выводить результат в лог.
    val sum = { num1: Int, num2: Int -> Log.i(TAG, "Sum = ${num1 + num2}") }

    // Вывести в лог для каждого объекта тип, количество строк и цену в евро в отформатированном виде.
    fun printPublicationInfo(publication: Publication) {
        Log.i(TAG, "Publication type = ${publication.getType()}\n" +
                "Words = ${publication.wordCount}\n" +
                "Price = ${publication.price} €")
    }

    // Создать метод buy, который в качестве параметра принимает Publication (notnull - значения) и
    // выводит в лог “The purchase is complete. The purchase amount was [цена издания]”.
    private fun buy(publication: Publication) {
        Log.i(TAG, "The purchase is complete. The purchase amount was ${publication.price}")
    }

    // Используя функцию let, попробуйте вызвать метод buy с каждой из переменных.
    fun callBuy(publication: Publication?) {
        publication?.let(::buy)
    }

    companion object {
        private const val TAG = "TestClassKotlinPart1"
    }
}