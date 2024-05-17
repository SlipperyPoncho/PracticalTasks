package com.artem.android.task1.domain

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val categoriesRepository: CategoriesRepository,
    private val mapper: Mapper
) {
    @Suppress("kotlin:S6531")
    suspend operator fun invoke(categoriesFromAssets: String): MutableLiveData<List<CategoryModel>> {
        return categoriesRepository.getCategories(categoriesFromAssets).map {
            mapper.mapCategories(it)
        } as MutableLiveData<List<CategoryModel>>
    }
}