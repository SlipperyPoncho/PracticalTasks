package com.artem.android.core.data

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.artem.android.core.domain.models.EventModel
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.io.BufferedReader
import java.io.InputStreamReader

fun readJSONFromAssets(context: Context, path: String): String {
    try {
        val file = context.assets.open(path)
        val bufferedReader = BufferedReader(InputStreamReader(file))
        val stringBuilder = StringBuilder()

        bufferedReader.useLines { lines ->
            lines.forEach {
                stringBuilder.append(it)
            }
        }

        return stringBuilder.toString()
    } catch (e: Exception) {
        e.printStackTrace()
        return ""
    }
}

@SuppressLint("DiscouragedApi")
fun getDrawableFromName(context: Context, drawableName: String): Drawable? {
    val resourceId = context.resources.getIdentifier(drawableName, "drawable", context.packageName)
    return if (resourceId != 0) {
        ContextCompat.getDrawable(context, resourceId)
    } else {
        null
    }
}

private fun monthFormatter(month: Month): Map<String, String> {
    val map: HashMap<String, String> = HashMap()
    when (month) {
        java.time.Month.JANUARY -> map["Январь"] = "Января"
        java.time.Month.FEBRUARY -> map["Февраль"] = "Февраля"
        java.time.Month.MARCH -> map["Март"] = "Марта"
        java.time.Month.APRIL -> map["Апрель"] = "Апреля"
        java.time.Month.MAY -> map["Май"] = "Мая"
        java.time.Month.JUNE -> map["Июнь"] = "Июня"
        java.time.Month.JULY -> map["Июль"] = "Июля"
        java.time.Month.AUGUST -> map["Август"] = "Августа"
        java.time.Month.SEPTEMBER -> map["Сентябрь"] = "Сентября"
        java.time.Month.OCTOBER -> map["Октябрь"] = "Октября"
        java.time.Month.NOVEMBER -> map["Ноябрь"] = "Ноября"
        java.time.Month.DECEMBER -> map["Декабрь"] = "Декабря"
    }
    return map
}

private fun stringDate(date: Int): String {
    return when {
        date < 10 -> "0$date"
        else -> date.toString()
    }
}

private fun stringDateRange(range: Int): String {
    val str = java.lang.StringBuilder()
    var firstWord = ""
    var lastWord = ""
    if (range == 1 || range == 21 || range == 31) {
        firstWord = "Остался "
        lastWord = " день"
    }
    if (range in 2..4 || range in 22..24) {
        firstWord = "Осталось "
        lastWord = " дня"
    }
    if (range in 5..20 || range in 25..30) {
        firstWord = "Осталось "
        lastWord = " дней"
    }
    str.append(firstWord + range + lastWord)
    return str.toString()
}

fun setDateText(event: EventModel?): String? {
    val currentDate = Clock.System.now().toLocalDateTime(TimeZone.of("UTC+3"))
    val eventDateStart = event?.eventDateStart?.let { Instant.fromEpochMilliseconds(it) }
        ?.toLocalDateTime(TimeZone.of("UTC+3"))
    val eventDateFinish = event?.eventDateFinish?.let { Instant.fromEpochMilliseconds(it) }
        ?.toLocalDateTime(TimeZone.of("UTC+3"))
    if (eventDateStart != null && eventDateFinish != null) {
        return when {
            eventDateStart == eventDateFinish -> {
                "${eventDateStart.dayOfMonth} " +
                        "${monthFormatter(eventDateStart.month).values.first()} " +
                        "${eventDateStart.year}"
            }
            eventDateStart < currentDate && currentDate < eventDateFinish -> {
                stringDateRange(eventDateFinish.dayOfMonth - currentDate.dayOfMonth) +
                        " (${stringDate(eventDateStart.dayOfMonth)}." +
                        "${stringDate(eventDateStart.monthNumber)})" +
                        " - (${stringDate(eventDateFinish.dayOfMonth)}." +
                        "${stringDate(eventDateFinish.monthNumber)})"
            }
            else -> { "${eventDateStart.dayOfMonth} " +
                    "${monthFormatter(eventDateStart.month).values.first()} " +
                    "${eventDateStart.year} - " +
                    "${eventDateFinish.dayOfMonth} " +
                    "${monthFormatter(eventDateFinish.month).values.first()} " +
                    "${eventDateFinish.year}"
            }
        }
    }
    return null
}