package com.artem.android.profilefeature

import dagger.Subcomponent

@Subcomponent
interface ProfileComponent {
    fun inject(fragment: ProfileFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): ProfileComponent
    }

}