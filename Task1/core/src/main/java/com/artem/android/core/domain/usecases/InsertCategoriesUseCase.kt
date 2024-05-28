package com.artem.android.core.domain.usecases

import com.artem.android.core.data.entities.Category
import com.artem.android.core.domain.repositories.CategoriesRepository
import javax.inject.Inject

class InsertCategoriesUseCase @Inject constructor(
    private val categoriesRepository: CategoriesRepository
) {
    @Suppress("kotlin:S6531")
    suspend operator fun invoke(categories: List<Category>) {
        categoriesRepository.insertCategories(categories)
    }
}