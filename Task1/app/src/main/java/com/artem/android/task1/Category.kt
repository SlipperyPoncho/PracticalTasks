package com.artem.android.task1

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class Category(
    @PrimaryKey val title: String = "",
    val image: String? = null,
    @ColumnInfo(name = "category_type")
    val categoryType: CategoryType? = null
)