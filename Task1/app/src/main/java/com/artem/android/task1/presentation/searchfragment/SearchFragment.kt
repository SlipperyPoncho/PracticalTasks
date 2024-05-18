package com.artem.android.task1.presentation.searchfragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.artem.android.task1.App
import com.artem.android.task1.R
import com.artem.android.task1.presentation.searchfragment.viewmodel.SearchFragmentViewModel
import com.artem.android.task1.presentation.searchfragment.adapter.SearchResultAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class SearchFragment: Fragment() {

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var searchView: SearchView

    private val searchFragmentViewModel: SearchFragmentViewModel by viewModels {
        (requireActivity().application as App).appComponent.searchFragmentViewModelFactory()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        searchFragmentViewModel.getEvents()
    }

    @SuppressLint("CheckResult")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        tabLayout = view.findViewById(R.id.tab_layout)
        viewPager = view.findViewById(R.id.view_pager)
        viewPager.adapter = SearchResultAdapter(this)
        searchView = view.findViewById(R.id.search_view)

        searchFragmentViewModel.searchEvents.observe(viewLifecycleOwner) {
            searchFragmentViewModel.setEvents(it)
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    searchFragmentViewModel.updateSearchQuery(query)
                }
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    searchFragmentViewModel.updateSearchQuery(newText)
                }
                return true
            }
        })

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

    companion object {
        fun newInstance(): Fragment {
            return SearchFragment()
        }
    }
}