package com.artem.android.searchfeature.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.artem.android.searchfeature.searchresulteventsfragment.SearchResultEventsFragment
import com.artem.android.searchfeature.searchresultnkofragment.SearchResultNKOFragment

class SearchResultAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> SearchResultEventsFragment.newInstance()
            1 -> SearchResultNKOFragment.newInstance()
            else -> SearchResultEventsFragment.newInstance()
        }
    }
}