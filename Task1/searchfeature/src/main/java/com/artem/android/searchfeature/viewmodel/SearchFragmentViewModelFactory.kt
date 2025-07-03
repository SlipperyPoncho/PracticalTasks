package com.artem.android.searchfeature.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

class SearchFragmentViewModelFactory @Inject constructor(
    viewModelProvider: Provider<SearchFragmentViewModel>
): ViewModelProvider.Factory {

    private val providers = mapOf<Class<*>, Provider<out ViewModel>>(
        SearchFragmentViewModel::class.java to viewModelProvider
    )

    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        return providers[modelClass]!!.get() as T
    }
}