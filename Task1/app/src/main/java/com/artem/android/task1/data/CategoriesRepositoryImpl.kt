package com.artem.android.task1.data

import androidx.lifecycle.LiveData
import com.artem.android.task1.data.database.CharityDao
import com.artem.android.task1.domain.CategoriesRepository
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonParser
import javax.inject.Inject

class CategoriesRepositoryImpl @Inject constructor(
    private val retrofitApi: RetrofitApi,
    private val charityDao: CharityDao
): CategoriesRepository {

    private suspend fun categoriesFromJSON(jsonFromAssets: String) {
        val jsonArray: JsonArray = JsonParser.parseString(jsonFromAssets).asJsonArray
        val categories: MutableList<Category> = mutableListOf()

        for (json in jsonArray) {
            val categoryData = Gson().fromJson(json, Category::class.java)
            categories.add(categoryData)
        }
        charityDao.insertAllCategories(categories)
    }

    override suspend fun getCategories(categoriesFromAssets: String): LiveData<List<Category>> {
        try {
            val response = retrofitApi.getCategories()
            if (response.isSuccessful) {
                val data = response.body()
                if (data != null) {
                    charityDao.insertAllCategories(data)
                }
            } else {
               categoriesFromJSON(categoriesFromAssets)
            }
        } catch (e: Exception) {
            categoriesFromJSON(categoriesFromAssets)
        }
        return charityDao.getCategories()
    }
}