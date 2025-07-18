package com.artem.android.newsfeature

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.artem.android.newsfeature.filterfragment.FilterFragment
import com.artem.android.newsfeature.viewmodel.NewsFragmentViewModel
import com.artem.android.newsfeature.eventdetailfragment.EventDetailFragment
import java.util.UUID

class NewsFragment: Fragment() {

    private lateinit var newsComponent: NewsComponent
    private lateinit var composeView: ComposeView

    private val newsFragmentViewModel: NewsFragmentViewModel by viewModels {
        newsComponent.newsFragmentViewModelFactory()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        newsComponent = (requireActivity().applicationContext as NewsComponentProvider)
            .provideNewsComponent()
        newsComponent.inject(this)

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

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val eventId = arguments?.getSerializable(ARG_EVENT_ID, UUID::class.java)
        newsFragmentViewModel.getEvents {
            Handler(Looper.getMainLooper()).postDelayed ({
                if (eventId != null) {
                    childFragmentManager.beginTransaction()
                        .replace(
                            R.id.news_fragment_container,
                            EventDetailFragment.newInstance(eventId)
                        )
                        .addToBackStack(null)
                        .commit()
                }
            }, 2000)
        }
    }

    @SuppressLint("CheckResult")
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_news, container, false)
        composeView = view.findViewById(R.id.compose_view)
        composeView.setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        composeView.setContent {
            NewsScreen(
                newsViewModel = newsFragmentViewModel,
                context = requireContext(),
                onNewsClick = {
                    childFragmentManager.beginTransaction()
                        .replace(R.id.news_fragment_container, EventDetailFragment.newInstance(it))
                        .addToBackStack(null)
                        .commit()
                },
                onFilterClick = {
                    childFragmentManager.beginTransaction()
                        .replace(R.id.news_fragment_container, FilterFragment.newInstance())
                        .addToBackStack(null)
                        .commit()
                }
            )
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsFragmentViewModel.newsEvents.observe(viewLifecycleOwner) { events ->
            newsFragmentViewModel.setEvents(events)
        }
    }

    companion object {
        private const val ARG_EVENT_ID = "eventId"

        fun newInstance(): Fragment {
            return NewsFragment()
        }

        fun newInstanceByNotification(eventId: UUID): Fragment {
            val args = Bundle().apply { putSerializable(ARG_EVENT_ID, eventId) }
            return NewsFragment().apply { arguments = args }
        }
    }
}