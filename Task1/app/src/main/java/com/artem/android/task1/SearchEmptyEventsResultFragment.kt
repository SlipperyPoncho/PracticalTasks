package com.artem.android.task1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class SearchEmptyEventsResultFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search_events_empty, container, false)
    }

    companion object {
        fun newInstance(): Fragment {
            return SearchEmptyEventsResultFragment()
        }
    }
}