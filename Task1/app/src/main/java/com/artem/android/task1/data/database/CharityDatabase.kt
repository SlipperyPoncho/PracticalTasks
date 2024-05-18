package com.artem.android.task1.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.artem.android.task1.data.entities.Category
import com.artem.android.task1.data.entities.Event

@Database(version = 1, entities = [Category::class, Event::class])
@TypeConverters(CharityTypeConverters::class)
abstract class CharityDatabase: RoomDatabase() {
    abstract fun charityDao(): CharityDao
}