package com.artem.android.task1.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.artem.android.task1.CategoryType

@Entity(tableName = "categories")
data class Category(
    @PrimaryKey val title: String = "",
    val image: String? = null,
    @ColumnInfo(name = "category_type")
    val categoryType: CategoryType? = null
)