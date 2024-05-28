package com.artem.android.searchfeature.searchresulteventsfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.artem.android.searchfeature.R
import com.artem.android.searchfeature.searchresulteventsfragment.adapter.SearchResultEventsAdapter
import com.artem.android.searchfeature.viewmodel.SearchFragmentViewModel

class SearchResultEventsFragment: Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SearchResultEventsAdapter
    private lateinit var topLine: View
    private lateinit var bottomLine: View

    private val searchFragmentViewModel: SearchFragmentViewModel by lazy {
        ViewModelProvider(requireParentFragment())[SearchFragmentViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search_events, container, false)
        topLine = view.findViewById(R.id.search_events_top_line)
        bottomLine = view.findViewById(R.id.search_events_bottom_line)

        recyclerView = view.findViewById(R.id.events_search_rv)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = SearchResultEventsAdapter()
        searchFragmentViewModel.searchResults.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                topLine.visibility = View.GONE
                bottomLine.visibility = View.GONE
            } else {
                topLine.visibility = View.VISIBLE
                bottomLine.visibility = View.VISIBLE
            }
            adapter.differ.submitList(it)
        }
        recyclerView.adapter = adapter

        return view
    }

    companion object {
        fun newInstance(): Fragment {
            return SearchResultEventsFragment()
        }
    }
}