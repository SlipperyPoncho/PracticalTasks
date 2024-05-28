package com.artem.android.core.data.repositories

import androidx.lifecycle.LiveData
import com.artem.android.core.data.RetrofitApi
import com.artem.android.core.data.database.CharityDao
import com.artem.android.core.data.entities.Category
import com.artem.android.core.domain.repositories.CategoriesRepository
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonParser
import javax.inject.Inject

class CategoriesRepositoryImpl @Inject constructor(
    private val retrofitApi: RetrofitApi,
    private val charityDao: CharityDao
): CategoriesRepository {

    private fun categoriesFromJSON(jsonFromAssets: String): List<Category> {
        val jsonArray: JsonArray = JsonParser.parseString(jsonFromAssets).asJsonArray
        val categories: MutableList<Category> = mutableListOf()

        for (json in jsonArray) {
            val categoryData = Gson().fromJson(json, Category::class.java)
            categories.add(categoryData)
        }
        return categories
    }

    override suspend fun requestCategories(categoriesFromAssets: String): List<Category> {
        try {
            val response = retrofitApi.getCategories()
            if (response.isSuccessful) {
                val data = response.body()
                if (data != null) {
                    return data
                }
            } else {
               return categoriesFromJSON(categoriesFromAssets)
            }
        } catch (e: Exception) {
            return categoriesFromJSON(categoriesFromAssets)
        }
        return emptyList()
    }

    override suspend fun insertCategories(categories: List<Category>) {
        charityDao.insertAllCategories(categories)
    }

    override suspend fun getCategories(): LiveData<List<Category>> {
        return charityDao.getCategories()
    }
}