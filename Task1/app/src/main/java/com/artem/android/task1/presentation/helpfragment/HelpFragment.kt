package com.artem.android.task1.presentation.helpfragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.artem.android.task1.App
import com.artem.android.task1.presentation.helpfragment.viewmodel.HelpFragmentViewModel
import com.artem.android.task1.R
import com.artem.android.task1.presentation.helpfragment.adapter.CategoryAdapter

class HelpFragment: Fragment() {

    private lateinit var recyclerView: RecyclerView

    private val helpFragmentViewModel: HelpFragmentViewModel by viewModels {
        (requireActivity().application as App).appComponent.helpFragmentViewModelFactory()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        helpFragmentViewModel.getCategories()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_help, container, false)
        recyclerView = view.findViewById(R.id.help_category_rv)
        recyclerView.layoutManager = GridLayoutManager(context, 2)

        helpFragmentViewModel.categories.observe(viewLifecycleOwner) {
            if (it != null) {
                recyclerView.adapter = CategoryAdapter(requireContext(), it)
            }
        }
        return view
    }

    companion object {
        fun newInstance(): Fragment {
            return HelpFragment()
        }
    }
}