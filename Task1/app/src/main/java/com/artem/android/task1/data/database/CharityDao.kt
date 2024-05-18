package com.artem.android.task1.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.artem.android.task1.data.entities.Category
import com.artem.android.task1.data.entities.Event

@Dao
interface CharityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = Category::class)
    suspend fun insertAllCategories(categories: List<Category>)

    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = Event::class)
    suspend fun insertAllEvents(events: List<Event>)

    @Query("SELECT * FROM categories")
    fun getCategories(): LiveData<List<Category>>

    @Query("SELECT * FROM events")
    fun getEvents(): LiveData<List<Event>>
}