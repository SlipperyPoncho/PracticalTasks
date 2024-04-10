package com.artem.android.task1

data class CategoryModel(
    val title: String = "",
    val image: String? = null,
    val categoryType: CategoryType? = null,
    val isChecked: Boolean = false
)