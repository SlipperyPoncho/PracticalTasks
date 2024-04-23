package com.artem.android.task1.domain

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.room.Room
import com.artem.android.task1.Category
import com.artem.android.task1.CategoryModel
import com.artem.android.task1.Event
import com.artem.android.task1.EventModel
import com.artem.android.task1.Mapper
import com.artem.android.task1.database.CharityDatabase
import java.lang.IllegalStateException

class DatabaseRepository private constructor(context: Context, private val mapper: Mapper) {

    private val database: CharityDatabase = Room.databaseBuilder(
        context.applicationContext,
        CharityDatabase::class.java,
        "charity_db"
    ).build()

    private val charityDao = database.charityDao()

    @Suppress("kotlin:S6531")
    fun getCategories(): MutableLiveData<List<CategoryModel>> = charityDao.getCategories().map {
        mapper.mapCategories(it)
    } as MutableLiveData<List<CategoryModel>>

    @Suppress("kotlin:S6531")
    fun getEvents(): MutableLiveData<List<EventModel>> = charityDao.getEvents().map {
        mapper.mapEvents(it)
    } as MutableLiveData<List<EventModel>>

    suspend fun insertAllCategories(categories: List<Category>) {
        charityDao.insertAllCategories(categories)
    }

    suspend fun insertAllEvents(events: List<Event>) {
        charityDao.insertAllEvents(events)
    }

    companion object {
        private var INSTANCE: DatabaseRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) INSTANCE = DatabaseRepository(context, Mapper())
        }
        fun get(): DatabaseRepository {
            return INSTANCE ?: throw  IllegalStateException("DatabaseRepository must be initialized")
        }
    }
}