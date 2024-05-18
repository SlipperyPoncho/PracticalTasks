package com.artem.android.task1.presentation.helpfragment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artem.android.task1.domain.models.CategoryModel
import com.artem.android.task1.domain.usecases.GetCategoriesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class HelpFragmentViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase
): ViewModel() {

    private var _categories: MutableLiveData<List<CategoryModel>> = MutableLiveData(emptyList())

    val categories: LiveData<List<CategoryModel>>
        get() = _categories

    @Suppress("kotlin:S6310")
    fun getCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            _categories = getCategoriesUseCase()
        }
    }
}