package com.artem.android.core.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.artem.android.core.data.CategoryType

@Entity(tableName = "categories")
data class Category(
    @PrimaryKey val title: String = "",
    val image: String? = null,
    @ColumnInfo(name = "category_type")
    val categoryType: CategoryType? = null
)