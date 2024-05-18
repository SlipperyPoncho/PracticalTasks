package com.artem.android.task1.domain.usecases

import com.artem.android.task1.data.entities.Category
import com.artem.android.task1.domain.repositories.CategoriesRepository
import javax.inject.Inject

class InsertCategoriesUseCase @Inject constructor(
    private val categoriesRepository: CategoriesRepository
) {
    @Suppress("kotlin:S6531")
    suspend operator fun invoke(categories: List<Category>) {
        categoriesRepository.insertCategories(categories)
    }
}