package com.artem.android.task1

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.jakewharton.rxbinding4.widget.queryTextChangeEvents
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy
import java.util.concurrent.TimeUnit

class SearchFragment: Fragment() {

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var searchView: SearchView

    private val charitySharedViewModel by lazy {
        ViewModelProvider(requireActivity())[CharitySharedViewModel::class.java]
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

        searchView.queryTextChangeEvents()
            .debounce(500, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onError = { it.printStackTrace() },
                onNext = {
                    charitySharedViewModel.updateSearchEvents(it.queryText.toString())
                }
            )

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