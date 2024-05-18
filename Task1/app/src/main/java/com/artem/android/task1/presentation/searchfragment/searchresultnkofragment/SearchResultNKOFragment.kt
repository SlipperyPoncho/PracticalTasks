package com.artem.android.task1.presentation.searchfragment.searchresultnkofragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.artem.android.task1.R
import com.artem.android.task1.presentation.searchfragment.searchresultnkofragment.adapter.SearchResultNKOAdapter

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

    companion object {
        fun newInstance(): Fragment {
            return SearchResultNKOFragment()
        }
    }
}