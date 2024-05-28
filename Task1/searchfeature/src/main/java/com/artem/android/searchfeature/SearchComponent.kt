package com.artem.android.searchfeature

import com.artem.android.searchfeature.viewmodel.SearchFragmentViewModelFactory
import dagger.Subcomponent

@Subcomponent
interface SearchComponent {
    fun inject(fragment: SearchFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): SearchComponent
    }

    fun searchFragmentViewModelFactory(): SearchFragmentViewModelFactory
}