package com.artem.android.task1.di.modules

import com.artem.android.task1.data.repositories.CategoriesRepositoryImpl
import com.artem.android.task1.domain.repositories.CategoriesRepository
import dagger.Binds
import dagger.Module

@Module
abstract class CategoriesRepoModule {
    @Binds
    abstract fun bindCategoriesRepo(categoriesRepositoryImpl: CategoriesRepositoryImpl):
            CategoriesRepository
}