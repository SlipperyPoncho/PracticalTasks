package com.artem.android.core.domain.models

import com.artem.android.core.data.CategoryType

data class CategoryModel(
    val title: String = "",
    val image: String? = null,
    val categoryType: CategoryType? = null,
    val isChecked: Boolean = false
)