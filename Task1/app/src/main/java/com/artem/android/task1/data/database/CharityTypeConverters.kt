package com.artem.android.task1.data.database

import androidx.room.TypeConverter
import com.artem.android.task1.data.CategoryType
import java.util.UUID

class CharityTypeConverters {
    @TypeConverter
    fun toUUID(uuid: String?): UUID? {
        return UUID.fromString(uuid)
    }

    @TypeConverter
    fun fromUUID(uuid: UUID?): String? {
        return uuid?.toString()
    }

    @TypeConverter
    fun fromListString(list: List<String>?): String? {
        return list?.toString()
    }

    @TypeConverter
    fun toListString(str: String?): List<String> {
        val result = ArrayList<String>()
        var split = emptyList<String>()
        if (str != null) {
            split = str.replace("[", "")
                .replace("]", "")
                .replace(" ", "")
                .split(",")
        }
        for (n in split) {
            try {
                result.add(n)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return result
    }

    @TypeConverter
    fun fromCategoryType(categoryType: CategoryType?): String? {
        return categoryType?.toString()
    }

    @TypeConverter
    fun toCategoryType(str: String?): CategoryType? {
        return when (str) {
            "CHILD" -> CategoryType.CHILD
            "ADULT" -> CategoryType.ADULT
            "OLD" -> CategoryType.OLD
            "ANIMAL" -> CategoryType.ANIMAL
            "EVENTS" -> CategoryType.EVENTS
            else -> null
        }
    }

    @TypeConverter
    fun fromListCategoryType(list: List<CategoryType>?): String? {
        return list?.toString()
    }

    @TypeConverter
    fun toListCategoryType(str: String?): List<CategoryType> {
        val result = ArrayList<CategoryType>()
        var split = emptyList<String>()
        if (str != null) {
            split = str.replace("[", "")
                .replace("]", "")
                .replace(" ", "")
                .split(",")
        }
        for (n in split) {
            try {
                toCategoryType(n)?.let { result.add(it) }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return result
    }
}