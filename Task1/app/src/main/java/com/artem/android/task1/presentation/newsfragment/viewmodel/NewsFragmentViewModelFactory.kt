package com.artem.android.task1.presentation.newsfragment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

class NewsFragmentViewModelFactory @Inject constructor(
    viewModelProvider: Provider<NewsFragmentViewModel>
): ViewModelProvider.Factory {

    private val providers = mapOf<Class<*>, Provider<out ViewModel>>(
        NewsFragmentViewModel::class.java to viewModelProvider
    )

    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        return providers[modelClass]!!.get() as T
    }
}