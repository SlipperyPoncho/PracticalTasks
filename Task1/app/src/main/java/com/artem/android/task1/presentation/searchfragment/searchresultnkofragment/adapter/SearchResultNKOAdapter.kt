package com.artem.android.task1.presentation.searchfragment.searchresultnkofragment.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.artem.android.task1.R

class SearchResultNKOAdapter(private var searchResults: List<String>)
    : RecyclerView.Adapter<SearchResultNKOAdapter.SearchResultNKOHolder>() {

    inner class SearchResultNKOHolder(view: View): RecyclerView.ViewHolder(view) {

        private val searchResultTextView: TextView = view.findViewById(R.id.nko_list_item_tv)
        private val searchResultUnderline: View = view.findViewById(R.id.search_result_underline)

        fun bind(searchResult: String) {
            searchResultTextView.text = searchResult
        }

        fun bindLast(searchResult: String) {
            searchResultTextView.text = searchResult
            searchResultUnderline.visibility = View.GONE
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultNKOAdapter.SearchResultNKOHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_item_nko, parent, false)
        return SearchResultNKOHolder(view)
    }

    override fun onBindViewHolder(holder: SearchResultNKOAdapter.SearchResultNKOHolder, position: Int) {
        val searchResult = searchResults[position]
        if (position == searchResults.size - 1) {
            holder.bindLast(searchResult)
        }
        else {
            holder.bind(searchResult)
        }
    }

    override fun getItemCount() = searchResults.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list: List<String>) {
        searchResults = list
        this.notifyDataSetChanged()
    }
}