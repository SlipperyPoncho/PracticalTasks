package com.artem.android.task1

import android.content.Context
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
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.UUID

class NewsFragment: Fragment() {

    interface Callbacks { fun onEventSelected(eventId: UUID) }

    private var callbacks: Callbacks? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var filterImageView: ImageView
    private lateinit var adapter: EventAdapter

    private val eventViewModel by lazy {
        ViewModelProvider(this)[EventViewModel::class.java]
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val eventsFromJSON = context?.let { eventViewModel.eventFromJSON(it) }
        if (eventsFromJSON != null) {
            events = eventsFromJSON
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_news, container, false)
        recyclerView = view.findViewById(R.id.news_rv)

        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = EventAdapter()
        adapter.differ.submitList(events)
        recyclerView.adapter = adapter

        filterImageView = view.findViewById(R.id.news_filter_iv)

        parentFragmentManager.setFragmentResultListener("listRequestKey", viewLifecycleOwner) {
                _, bundle ->
            val result = bundle.getParcelableArrayList("listBundleKey", Event::class.java)
            if (result != null) {
                adapter.differ.submitList(result)
            }
        }

        return view
    }

    override fun onStart() {
        super.onStart()
        filterImageView.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, FilterFragment.newInstance(events))
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    private inner class EventHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener {

        private lateinit var event: Event
        private val eventTitle: TextView = itemView.findViewById(R.id.event_title_tv)
        private val eventImg: ImageView = itemView.findViewById(R.id.event_iv)
        private val eventDetails: TextView = itemView.findViewById(R.id.event_detail_tv)
        private val eventDate: TextView = itemView.findViewById(R.id.event_date_tv)

        init { itemView.setOnClickListener(this) }

        fun bind(event: Event) {
            this.event = event
            eventTitle.text = event.title
            event.imageResId?.let { eventImg.setImageResource(it) }
            eventDetails.text = event.eventDetails
            eventDate.text = eventViewModel.setDateText(event)
        }

        override fun onClick(v: View?) {
            callbacks?.onEventSelected(event.id)
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

        private val differCallback = object : DiffUtil.ItemCallback<Event>() {
            override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {
                return oldItem == newItem
            }
        }

        val differ = AsyncListDiffer(this, differCallback)
    }

    companion object {
        lateinit var events: List<Event>
        fun newInstance(): Fragment {
            return NewsFragment()
        }
    }
}