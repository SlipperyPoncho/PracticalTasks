package com.artem.android.task1.presentation.searchfragment.searchresulteventsfragment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.artem.android.task1.R
import com.artem.android.task1.domain.models.EventModel

class SearchResultEventsAdapter
    : RecyclerView.Adapter<SearchResultEventsAdapter.SearchResultEventsHolder>() {

    inner class SearchResultEventsHolder(view: View): RecyclerView.ViewHolder(view) {

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
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            SearchResultEventsAdapter.SearchResultEventsHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_item_search_events, parent, false)
        return SearchResultEventsHolder(view)
    }

    override fun onBindViewHolder(holder: SearchResultEventsAdapter.SearchResultEventsHolder,
                                  position: Int) {
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