package com.artem.android.newsfeature.eventdetailfragment.moneydialogfragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.artem.android.newsfeature.R
import com.artem.android.newsfeature.eventdetailfragment.MyWorker
import java.util.UUID

class MoneyDialogFragment: DialogFragment() {
    private lateinit var moneyField: EditText
    private lateinit var cancelBtn: TextView
    private lateinit var sendMoneyBtn: TextView

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_money, container, false)

        cancelBtn = view.findViewById(R.id.cancel_tv)
        moneyField = view.findViewById(R.id.sum_et)
        sendMoneyBtn = view.findViewById(R.id.send_money_tv)

        cancelBtn.setOnClickListener {
            this.dismiss()
        }

        val eventId = arguments?.getSerializable(ARG_EVENT_ID, UUID::class.java)
        val eventTitle = arguments?.getSerializable(ARG_EVENT_TITLE, String::class.java)

        sendMoneyBtn.setOnClickListener {
            val data = Data.Builder()
                .putString("uuid", eventId.toString())
                .putString("title", eventTitle)
                .putInt("sum", moneyField.text.toString().toInt())
                .build()

            val workRequest = OneTimeWorkRequest.Builder(MyWorker::class.java)
                .setInputData(data)
                .setConstraints(
                    Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.CONNECTED)
                        .setRequiresCharging(true)
                        .build()
                ).build()

            WorkManager.getInstance(requireContext()).enqueue(workRequest)
        }

        return view
    }

    companion object {
        private const val ARG_EVENT_ID = "eventId"
        private const val ARG_EVENT_TITLE = "eventTitle"
        fun newInstance(eventId: UUID, eventTitle: String): DialogFragment {
            val args = Bundle().apply {
                putSerializable(ARG_EVENT_ID, eventId)
                putSerializable(ARG_EVENT_TITLE, eventTitle)
            }
            return MoneyDialogFragment().apply { arguments = args }
        }
    }
}