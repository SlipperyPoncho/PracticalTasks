package com.artem.android.task1.presentation.mainviewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artem.android.task1.domain.usecases.InsertCategoriesUseCase
import com.artem.android.task1.domain.usecases.InsertEventsUseCase
import com.artem.android.task1.domain.usecases.RequestCategoriesUseCase
import com.artem.android.task1.domain.usecases.RequestEventsUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(
    private val requestCategoriesUseCase: RequestCategoriesUseCase,
    private val insertCategoriesUseCase: InsertCategoriesUseCase,
    private val requestEventsUseCase: RequestEventsUseCase,
    private val insertEventsUseCase: InsertEventsUseCase,
): ViewModel() {

    private val errorLiveData = MutableLiveData<String>()
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, e ->
        errorLiveData.value = e.message
    }

    fun initializeData(categoriesFromAssets: String, eventsFromAssets: String) {
        categoriesFromServer(categoriesFromAssets)
        eventsFromServer(eventsFromAssets)
    }

    @Suppress("kotlin:S6310")
    private fun categoriesFromServer(categoriesFromAssets: String) {
        viewModelScope.launch(coroutineExceptionHandler) {
            withContext(Dispatchers.IO) {
                try {
                    val categories = requestCategoriesUseCase(categoriesFromAssets)
                    insertCategoriesUseCase(categories)
                } catch (e: Exception) {
                    errorLiveData.postValue(e.message)
                }
            }
        }
    }

    @Suppress("kotlin:S6310")
    private fun eventsFromServer(eventsFromAssets: String) {
        viewModelScope.launch(coroutineExceptionHandler) {
            withContext(Dispatchers.IO) {
                try {
                    val events = requestEventsUseCase(eventsFromAssets)
                    insertEventsUseCase(events)
                } catch (e: Exception) {
                    errorLiveData.postValue(e.message)
                }
            }
        }
    }
}