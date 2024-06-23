package com.artem.android.newsfeature.eventdetailfragment

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.artem.android.core.domain.models.EventModel
import com.artem.android.core.data.getDrawableFromName
import com.artem.android.newsfeature.viewmodel.NewsFragmentViewModel
import com.artem.android.core.data.setDateText
import com.artem.android.newsfeature.R
import com.artem.android.newsfeature.eventdetailfragment.moneydialogfragment.MoneyDialogFragment
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
    private lateinit var moneyBtn: ImageButton
    private var dialogFragment: DialogFragment? = null

    private val newsFragmentViewModel: NewsFragmentViewModel by lazy {
        ViewModelProvider(requireParentFragment())[NewsFragmentViewModel::class.java]
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
        moneyBtn = view.findViewById(R.id.coins_btn)

        val eventId = arguments?.getSerializable(ARG_EVENT_ID, UUID::class.java)
        if (eventId != null) {
            newsFragmentViewModel.findEventById(eventId)?.let {
                updateUI(it)
                newsFragmentViewModel.updateUnreadNewsCounter(it.id)
            }
        }

        moneyBtn.setOnClickListener {
            if (eventId != null) {
                dialogFragment = MoneyDialogFragment.newInstance(eventId, eventTitle.text.toString())
                dialogFragment?.show(this@EventDetailFragment.childFragmentManager, SEND_MONEY)
            }
        }

        return view
    }


    @SuppressLint("SetTextI18n")
    private fun updateUI(event: EventModel) {
        toolBarTitle.text = event.title
        eventTitle.text = event.title
        eventDate.text = setDateText(event)
        sponsorText.text = event.sponsor
        eventAddress.text = event.address
        contactPhone.text = event.phoneNumbers

        event.images?.getOrNull(0)?.let {
            mainImage.setImageDrawable(getDrawableFromName(requireContext(), it))
        }
        event.images?.getOrNull(1)?.let {
            additionalImage1.setImageDrawable(getDrawableFromName(requireContext(), it))
        }
        event.images?.getOrNull(2)?.let {
            additionalImage2.setImageDrawable(getDrawableFromName(requireContext(), it))
        }

        eventDetailText1.text = event.detailsText1
        eventDetailText2.text = event.detailsText2
    }

    companion object {
        private const val ARG_EVENT_ID = "eventId"
        private const val SEND_MONEY = "SendMoney"
        fun newInstance(eventId: UUID): EventDetailFragment {
            val args = Bundle().apply { putSerializable(ARG_EVENT_ID, eventId) }
            return EventDetailFragment().apply { arguments = args }
        }
    }
}