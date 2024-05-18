package com.artem.android.task1.domain.repositories

import androidx.lifecycle.LiveData
import com.artem.android.task1.data.entities.Category

interface CategoriesRepository {
    suspend fun requestCategories(categoriesFromAssets: String): List<Category>
    suspend fun insertCategories(categories: List<Category>)
    suspend fun getCategories(): LiveData<List<Category>>
}