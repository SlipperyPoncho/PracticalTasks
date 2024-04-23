package com.artem.android.task1

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
@Entity(tableName = "events")
data class Event(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    val title: String = "",
    val images: List<String>? = null,
    @ColumnInfo(name = "event_details")
    val eventDetails: String = "",
    @ColumnInfo(name = "event_date_start")
    val eventDateStart: Long = 0,
    @ColumnInfo(name = "event_date_finish")
    val eventDateFinish: Long = 0,
    val sponsor: String = "",
    val address: String = "",
    @ColumnInfo(name = "phone_numbers")
    val phoneNumbers: String = "",
    @ColumnInfo(name = "details_text1")
    val detailsText1: String = "",
    @ColumnInfo(name = "details_text2")
    val detailsText2: String = "",
    val categories: List<CategoryType> = arrayListOf()
) : Parcelable