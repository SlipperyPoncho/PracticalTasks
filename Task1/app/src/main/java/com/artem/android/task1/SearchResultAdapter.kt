package com.artem.android.task1

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SearchResultAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> SearchResultEventsFragment.newInstance()
            1 -> SearchResultNKOFragment.newInstance()
            else -> SearchEmptyEventsResultFragment.newInstance()
        }
    }
}