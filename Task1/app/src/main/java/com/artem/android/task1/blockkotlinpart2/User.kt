package com.artem.android.task1.blockkotlinpart2

import java.time.LocalTime

// Реализовать класс данных User с полями id, name, age и type.
// У класса User создать ленивое свойство startTime, в котором получаем текущее время.
data class User(val id: Int = 0,
                val name: String = "",
                val age: Int = 0,
                val type: Type = Type.DEMO) {
    val startTime: LocalTime by lazy { LocalTime.now() }
}
