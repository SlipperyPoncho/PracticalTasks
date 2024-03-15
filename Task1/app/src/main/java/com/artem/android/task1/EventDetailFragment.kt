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
import java.util.UUID

class EventDetailFragment: Fragment() {

    private lateinit var toolBarTitle: TextView
    private lateinit var eventTitle: TextView
    private lateinit var eventDate: TextView
    private lateinit var sponsorText: TextView
    private lateinit var eventAddress: TextView
    private lateinit var contactPhone: TextView
    private lateinit var mainImage: ImageView
    private lateinit var additionalImage1: ImageView
    private lateinit var additionalImage2: ImageView
    private lateinit var eventDetailText1: TextView
    private lateinit var eventDetailText2: TextView

    private val charitySharedViewModel by lazy {
        ViewModelProvider(requireActivity())[CharitySharedViewModel::class.java]
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_event_detail, container, false)
        toolBarTitle = view.findViewById(R.id.event_detail_toolbar_title)
        eventTitle = view.findViewById(R.id.event_detail_title_tv)
        eventDate = view.findViewById(R.id.event_detail_date_tv)
        sponsorText = view.findViewById(R.id.sponsor_tv)
        eventAddress = view.findViewById(R.id.event_detail_address_tv)
        contactPhone = view.findViewById(R.id.event_detail_phone_tv)
        mainImage = view.findViewById(R.id.event_detail_image1_iv)
        additionalImage1 = view.findViewById(R.id.event_detail_image2_iv)
        additionalImage2 = view.findViewById(R.id.event_detail_image3_iv)
        eventDetailText1 = view.findViewById(R.id.event_detail_text1_tv)
        eventDetailText2 = view.findViewById(R.id.event_detail_text2_tv)

        val eventId = arguments?.getSerializable(ARG_EVENT_ID, UUID::class.java)
        if (eventId != null) {
            charitySharedViewModel.findEventById(eventId)?.let { updateUI(it) }
        }

        return view
    }

    @SuppressLint("SetTextI18n")
    private fun updateUI(event: Event) {
        toolBarTitle.text = event.title
        eventTitle.text = event.title
        eventDate.text = setDateText(event)
        sponsorText.text = event.sponsor
        eventAddress.text = event.address
        contactPhone.text = event.phoneNumbers
        event.imageResId?.let { mainImage.setImageResource(it) }
        event.additionalImageResId?.let { additionalImage1.setImageResource(it) }
        event.additionalImage2ResId?.let { additionalImage2.setImageResource(it) }
        eventDetailText1.text = event.detailsText1
        eventDetailText2.text = event.detailsText2
    }

    companion object {
        private const val ARG_EVENT_ID = "EventID"
        fun newInstance(eventId: UUID): EventDetailFragment {
            val args = Bundle().apply { putSerializable(ARG_EVENT_ID, eventId) }
            return EventDetailFragment().apply { arguments = args }
        }
    }
}