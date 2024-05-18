package com.artem.android.task1.presentation.helpfragment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

class HelpFragmentViewModelFactory @Inject constructor(
    viewModelProvider: Provider<HelpFragmentViewModel>
): ViewModelProvider.Factory {

    private val providers = mapOf<Class<*>, Provider<out ViewModel>>(
        HelpFragmentViewModel::class.java to viewModelProvider
    )

    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        return providers[modelClass]!!.get() as T
    }
}