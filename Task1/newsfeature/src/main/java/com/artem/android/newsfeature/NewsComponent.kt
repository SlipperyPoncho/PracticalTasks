package com.artem.android.newsfeature

import com.artem.android.newsfeature.viewmodel.NewsFragmentViewModelFactory
import dagger.Subcomponent

@Subcomponent
interface NewsComponent {
    fun inject(fragment: NewsFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): NewsComponent
    }

    fun newsFragmentViewModelFactory(): NewsFragmentViewModelFactory
}