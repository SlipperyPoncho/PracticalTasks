package com.artem.android.core.domain.usecases

import com.artem.android.core.data.entities.Category
import com.artem.android.core.domain.repositories.CategoriesRepository
import javax.inject.Inject

class RequestCategoriesUseCase @Inject constructor(
    private val categoriesRepository: CategoriesRepository
) {
    @Suppress("kotlin:S6531")
    suspend operator fun invoke(categoriesFromAssets: String): List<Category> {
        return categoriesRepository.requestCategories(categoriesFromAssets)
    }
}