package com.artem.android.splashfeature

import dagger.Subcomponent

@Subcomponent
interface SplashComponent {
    fun inject(fragment: SplashFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): SplashComponent
    }
}