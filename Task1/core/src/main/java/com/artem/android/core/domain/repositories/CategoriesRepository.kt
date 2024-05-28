package com.artem.android.core.domain.repositories

import androidx.lifecycle.LiveData
import com.artem.android.core.data.entities.Category

interface CategoriesRepository {
    suspend fun requestCategories(categoriesFromAssets: String): List<Category>
    suspend fun insertCategories(categories: List<Category>)
    suspend fun getCategories(): LiveData<List<Category>>
}