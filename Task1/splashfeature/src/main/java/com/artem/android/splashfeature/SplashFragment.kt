package com.artem.android.splashfeature

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class SplashFragment: Fragment() {

    private lateinit var splashComponent: SplashComponent

    override fun onAttach(context: Context) {
        super.onAttach(context)
        splashComponent = (requireActivity().applicationContext as SplashComponentProvider)
            .provideSplashComponent()
        splashComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    companion object {
        fun newInstance(): Fragment {
            return SplashFragment()
        }
    }
}