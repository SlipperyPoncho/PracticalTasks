package com.artem.android.task1.domain.usecases

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.artem.android.task1.domain.repositories.CategoriesRepository
import com.artem.android.task1.domain.models.CategoryModel
import com.artem.android.task1.domain.mapper.Mapper
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val categoriesRepository: CategoriesRepository,
    private val mapper: Mapper
) {
    @Suppress("kotlin:S6531")
    suspend operator fun invoke(): MutableLiveData<List<CategoryModel>> {
        return categoriesRepository.getCategories().map {
            mapper.mapCategories(it)
        } as MutableLiveData<List<CategoryModel>>
    }
}