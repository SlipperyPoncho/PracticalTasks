package com.artem.android.task1

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SearchResultNKOFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SearchResultNKOAdapter

    private val searchResults: List<String> = arrayListOf(
        "Благотворительный фонд Алины Кабаевой",
        "«Во имя жизни»",
        "Благотворительный Фонд В. Потанина",
        "«Детские домики»",
        "«Мозаика счастья»"
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_search_nko, container, false)
        recyclerView = view.findViewById(R.id.nko_rv)
        recyclerView.layoutManager = LinearLayoutManager(context)
        val shuffledResults = searchResults.shuffled()
        adapter = SearchResultNKOAdapter(shuffledResults)
        recyclerView.adapter = adapter
        return view
    }

    override fun onResume() {
        super.onResume()
        adapter.updateList(searchResults.shuffled())
    }

    private inner class SearchResultHolder(view: View): RecyclerView.ViewHolder(view) {

        private val searchResultTextView: TextView = view.findViewById(R.id.nko_list_item_tv)

        fun bind(searchResult: String) {
            searchResultTextView.text = searchResult
        }
    }

    private inner class SearchResultNKOAdapter(private var searchResults: List<String>)
        : RecyclerView.Adapter<SearchResultHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.list_item_nko, parent, false)
            return SearchResultHolder(view)
        }

        override fun onBindViewHolder(holder: SearchResultHolder, position: Int) {
            val searchResult = searchResults[position]
            holder.bind(searchResult)
        }

        override fun getItemCount() = searchResults.size

        @SuppressLint("NotifyDataSetChanged")
        fun updateList(list: List<String>) {
            searchResults = list
            this.notifyDataSetChanged()
        }
    }

    companion object {
        fun newInstance(): Fragment {
            return SearchResultNKOFragment()
        }
    }
}