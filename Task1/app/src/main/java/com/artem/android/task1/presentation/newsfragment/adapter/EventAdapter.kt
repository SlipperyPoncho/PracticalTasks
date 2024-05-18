package com.artem.android.task1.presentation.newsfragment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.artem.android.task1.presentation.newsfragment.eventdetailfragment.EventDetailFragment
import com.artem.android.task1.R
import com.artem.android.task1.domain.models.EventModel
import com.artem.android.task1.data.getDrawableFromName
import com.artem.android.task1.data.setDateText

class EventAdapter(
    private val context: Context,
    private val fragmentManager: FragmentManager)
    : RecyclerView.Adapter<EventAdapter.EventHolder>() {

    inner class EventHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener {

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
                eventImg.setImageDrawable(getDrawableFromName(context, event.images[0]))
            }
            eventDetails.text = event.eventDetails
            eventDate.text = setDateText(event)
        }

        override fun onClick(v: View?) {
            fragmentManager.beginTransaction()
                .replace(R.id.news_fragment_container, EventDetailFragment.newInstance(event.id))
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventAdapter.EventHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_item_news, parent, false)
        return EventHolder(view)
    }

    override fun onBindViewHolder(holder: EventAdapter.EventHolder, position: Int) {
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