package com.artem.android.newsfeature

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.artem.android.newsfeature.filterfragment.FilterFragment
import com.artem.android.newsfeature.viewmodel.NewsFragmentViewModel
import com.artem.android.core.domain.models.EventModel
import com.artem.android.newsfeature.adapter.EventAdapter
import kotlinx.coroutines.launch

class NewsFragment: Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var filterImageView: ImageView
    private lateinit var adapter: EventAdapter
    private lateinit var newsCounterIV: ImageView
    private lateinit var newsCounterTV: TextView
    private lateinit var newsComponent: NewsComponent

    private val newsFragmentViewModel: NewsFragmentViewModel by viewModels {
        newsComponent.newsFragmentViewModelFactory()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        newsComponent = (requireActivity().applicationContext as NewsComponentProvider)
            .provideNewsComponent()
        newsComponent.inject(this)

        newsFragmentViewModel.getEvents()
        val backCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (childFragmentManager.backStackEntryCount > 0) {
                    childFragmentManager.popBackStack()
                    return
                }
                parentFragmentManager.popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, backCallback)
    }

    @SuppressLint("CheckResult")
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_news, container, false)
        filterImageView = view.findViewById(R.id.news_filter_iv)
        newsCounterIV = view.findViewById(R.id.unread_news_counter_iv)
        newsCounterTV = view.findViewById(R.id.unread_news_counter_tv)
        recyclerView = view.findViewById(R.id.news_rv)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = EventAdapter(requireContext(), childFragmentManager)

        newsFragmentViewModel.newsEvents.observe(viewLifecycleOwner) { events ->
            newsFragmentViewModel.setEvents(events)
            adapter.differ.submitList(events)
            filterImageView.setOnClickListener {
                childFragmentManager.beginTransaction()
                    .replace(R.id.news_fragment_container, FilterFragment.newInstance())
                    .addToBackStack(null)
                    .commit()
            }
        }

        lifecycleScope.launch {
            newsFragmentViewModel.unreadNewsCounter.collect {
                newsCounterTV.text = it.toString()
                if (newsCounterTV.text == "0") {
                    newsCounterIV.visibility = View.GONE
                    newsCounterTV.visibility = View.GONE
                }
                else {
                    newsCounterIV.visibility = View.VISIBLE
                    newsCounterTV.visibility = View.VISIBLE
                }
            }
        }

        recyclerView.adapter = adapter

        parentFragmentManager.setFragmentResultListener("listRequestKey", viewLifecycleOwner) {
                _, bundle ->
            val result = bundle.getParcelableArrayList("listBundleKey", EventModel::class.java)
            if (result != null) {
                adapter.differ.submitList(result)
            }
        }

        return view
    }

    companion object {
        fun newInstance(): Fragment {
            return NewsFragment()
        }
    }
}