package com.artem.android.task1

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class NewsFragment: Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var filterImageView: ImageView
    private lateinit var adapter: EventAdapter
    private lateinit var newsCounterIV: ImageView
    private lateinit var newsCounterTV: TextView

    private val charitySharedViewModel by lazy {
        ViewModelProvider(requireActivity())[CharitySharedViewModel::class.java]
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
        adapter = EventAdapter()

        charitySharedViewModel.newsEvents.observe(viewLifecycleOwner) { events ->
            adapter.differ.submitList(events)
            filterImageView.setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, FilterFragment.newInstance())
                    .addToBackStack(null)
                    .commit()
            }
        }

        lifecycleScope.launch {
            charitySharedViewModel.unreadNewsCounter.collect {
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

    private inner class EventHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener {

        private lateinit var event: EventModel
        private val eventTitle: TextView = itemView.findViewById(R.id.event_title_tv)
        private val eventImg: ImageView = itemView.findViewById(R.id.event_iv)
        private val eventDetails: TextView = itemView.findViewById(R.id.event_detail_tv)
        private val eventDate: TextView = itemView.findViewById(R.id.event_date_tv)

        init { itemView.setOnClickListener(this) }

        fun bind(event: EventModel) {
            this.event = event
            eventTitle.text = event.title
            if (event.images != null) {
                eventImg.setImageDrawable(getDrawableFromName(requireContext(), event.images[0]))
            }
            eventDetails.text = event.eventDetails
            eventDate.text = setDateText(event)
        }

        override fun onClick(v: View?) {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, EventDetailFragment.newInstance(event.id))
                .addToBackStack(null)
                .commit()
        }
    }

    private inner class EventAdapter : RecyclerView.Adapter<EventHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.list_item_news, parent, false)
            return EventHolder(view)
        }

        override fun onBindViewHolder(holder: EventHolder, position: Int) {
            holder.bind(differ.currentList[position])
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
            return NewsFragment()
        }
    }
}