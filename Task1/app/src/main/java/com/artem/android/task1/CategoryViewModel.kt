package com.artem.android.task1

import android.content.Context
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonParser

class CategoryViewModel: ViewModel() {
    fun categoryFromJSON(context: Context): List<Category> {
        val jsonString = readJSONFromAssets(context, "categories.json")
        val jsonArray: JsonArray = JsonParser.parseString(jsonString).asJsonArray
        val categories: MutableList<Category> = mutableListOf()

        for (json in jsonArray) {
            val categoryData = Gson().fromJson(json, Category::class.java)
            categories.add(categoryData)
        }

        return categories
    }
}