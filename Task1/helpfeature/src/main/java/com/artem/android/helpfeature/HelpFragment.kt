package com.artem.android.helpfeature

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.artem.android.helpfeature.adapter.CategoryAdapter
import com.artem.android.helpfeature.viewmodel.HelpFragmentViewModel

class HelpFragment: Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var helpComponent: HelpComponent

    private val helpFragmentViewModel: HelpFragmentViewModel by viewModels {
        helpComponent.helpFragmentViewModelFactory()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        helpComponent = (requireActivity().applicationContext as HelpComponentProvider)
            .provideHelpComponent()
        helpComponent.inject(this)
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