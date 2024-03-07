package com.artem.android.task1

import android.content.Context
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonParser
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.lang.StringBuilder
import java.util.UUID

class EventViewModel: ViewModel() {

    fun findEventById(events: List<Event>, eventId: UUID): Event? {
        for (event in events) {
            if (event.id == eventId) return event
        }
        return null
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
        val str = StringBuilder()
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

    fun setDateText(event: Event?): String? {
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

    fun eventFromJSON(context: Context): List<Event> {
        val jsonString = readJSONFromAssets(context, "events.json")
        val jsonArray: JsonArray = JsonParser.parseString(jsonString).asJsonArray
        val events: MutableList<Event> = mutableListOf()

        for (json in jsonArray) {
            val eventData = Gson().fromJson(json, Event::class.java)
            events.add(eventData)
        }

        return events
    }
}