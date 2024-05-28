package com.artem.android.helpfeature

import com.artem.android.helpfeature.viewmodel.HelpFragmentViewModelFactory
import dagger.Subcomponent

@Subcomponent
interface HelpComponent {
    fun inject(fragment: HelpFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): HelpComponent
    }

    fun helpFragmentViewModelFactory(): HelpFragmentViewModelFactory
}