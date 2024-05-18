package com.artem.android.task1.di.modules

import android.content.Context
import androidx.room.Room
import com.artem.android.task1.data.database.CharityDao
import com.artem.android.task1.data.database.CharityDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(context: Context): CharityDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            CharityDatabase::class.java,
            "charity_db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideDao(database: CharityDatabase): CharityDao {
        return database.charityDao()
    }
}