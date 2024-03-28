package com.artem.android.task1

import java.util.UUID

data class EventModel(
    val id: UUID = UUID.randomUUID(),
    val title: String = "",
    val imageResId: Int? = null,
    val eventDetails: String = "",
    val eventDateStart: Long = 0,
    val eventDateFinish: Long = 0,
    val sponsor: String = "",
    val address: String = "",
    val phoneNumbers: String = "",
    val additionalImageResId: Int? = null,
    val additionalImage2ResId: Int? = null,
    val detailsText1: String = "",
    val detailsText2: String = "",
    val categories: List<CategoryType> = arrayListOf(),
    var isRead: Boolean = false
)