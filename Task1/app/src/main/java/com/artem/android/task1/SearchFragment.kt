package com.artem.android.task1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class SearchFragment: Fragment() {

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var bottomNavBar: BottomNavigationView
    private lateinit var bottomNavBarHelp: View
    private lateinit var bottomNavBarProfile: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.search_fragment, container, false)
        tabLayout = view.findViewById(R.id.tab_layout)
        viewPager = view.findViewById(R.id.view_pager)
        viewPager.adapter = SearchResultAdapter(this)
        bottomNavBar = view.findViewById(R.id.bottom_nav_bar_search)
        bottomNavBar.selectedItemId = R.id.search
        bottomNavBarHelp = bottomNavBar.findViewById(R.id.help)
        bottomNavBarProfile = bottomNavBar.findViewById(R.id.profile)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "По мероприятиям"
                1 -> tab.text = "По НКО"
            }
        }.attach()
    }

    override fun onStart() {
        super.onStart()
        bottomNavBarHelp.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HelpFragment.newInstance())
                .commit()
        }
        bottomNavBarProfile.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ProfileFragment.newInstance())
                .commit()
        }
    }

    companion object {
        fun newInstance(): Fragment {
            return SearchFragment()
        }
    }
}