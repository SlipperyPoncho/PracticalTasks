package com.artem.android.task1.di

import com.artem.android.task1.data.CategoriesRepositoryImpl
import com.artem.android.task1.domain.CategoriesRepository
import dagger.Binds
import dagger.Module

@Module
abstract class CategoriesRepoModule {
    @Binds
    abstract fun bindCategoriesRepo(categoriesRepositoryImpl: CategoriesRepositoryImpl):
            CategoriesRepository
}