package com.artem.android.task1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SearchResultEventsFragment: Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SearchResultEventsAdapter
    private lateinit var topLine: View
    private lateinit var bottomLine: View

    private val charitySharedViewModel by lazy {
        ViewModelProvider(requireActivity())[CharitySharedViewModel::class.java]
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
        charitySharedViewModel.searchEvents.observe(viewLifecycleOwner) {
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

    private inner class SearchResultEventsHolder(view: View): RecyclerView.ViewHolder(view) {

        private val searchResultTextView: TextView = view.findViewById(R.id.search_events_list_item_tv)
        private val searchResultUnderline: View = view.findViewById(R.id.search_events_result_underline)

        fun bind(event: EventModel) {
            searchResultTextView.text = event.title
            searchResultUnderline.visibility = View.VISIBLE
        }

        fun bindLast(event: EventModel) {
            searchResultTextView.text = event.title
            searchResultUnderline.visibility = View.GONE
        }
    }

    private inner class SearchResultEventsAdapter : RecyclerView.Adapter<SearchResultEventsHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultEventsHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.list_item_search_events, parent, false)
            return SearchResultEventsHolder(view)
        }

        override fun onBindViewHolder(holder: SearchResultEventsHolder, position: Int) {
            if (position == differ.currentList.lastIndex) {
                holder.bindLast(differ.currentList[position])
            } else {
                holder.bind(differ.currentList[position])
            }
        }

        override fun getItemCount() = differ.currentList.size

        private val differCallback = object : DiffUtil.ItemCallback<EventModel>() {
            override fun areItemsTheSame(oldItem: EventModel, newItem: EventModel): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: EventModel, newItem: EventModel): Boolean {
                return oldItem == newItem
            }
        }

        val differ = AsyncListDiffer(this, differCallback)
    }

    companion object {
        fun newInstance(): Fragment {
            return SearchResultEventsFragment()
        }
    }
}