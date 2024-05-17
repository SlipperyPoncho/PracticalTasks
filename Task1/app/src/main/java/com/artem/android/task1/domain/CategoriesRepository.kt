package com.artem.android.task1.domain

import androidx.lifecycle.LiveData
import com.artem.android.task1.data.Category

interface CategoriesRepository {
    suspend fun getCategories(categoriesFromAssets: String): LiveData<List<Category>>
}