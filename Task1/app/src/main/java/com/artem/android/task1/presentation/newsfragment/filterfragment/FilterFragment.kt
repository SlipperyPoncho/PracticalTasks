package com.artem.android.task1.presentation.newsfragment.filterfragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.artem.android.task1.data.CategoryType
import com.artem.android.task1.R
import com.artem.android.task1.domain.models.CategoryModel
import com.artem.android.task1.presentation.newsfragment.filterfragment.adapter.FilterCategoryAdapter
import com.artem.android.task1.presentation.newsfragment.viewmodel.NewsFragmentViewModel

class FilterFragment: Fragment() {
    private lateinit var filterRecycler: RecyclerView
    private lateinit var filterOK: ImageView
    private var filteredCategories: MutableList<CategoryType> = mutableListOf()

    private val newsFragmentViewModel: NewsFragmentViewModel by lazy {
        ViewModelProvider(requireParentFragment())[NewsFragmentViewModel::class.java]
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        newsFragmentViewModel.getCategories()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_filter, container, false)
        filterOK = view.findViewById(R.id.filter_ok_iv)
        filterRecycler = view.findViewById(R.id.help_category_filter_rv)
        filterRecycler.layoutManager = LinearLayoutManager(context)
        newsFragmentViewModel.categories.observe(viewLifecycleOwner) { categories ->
            filterRecycler.adapter = FilterCategoryAdapter(categories)
            filterOK.setOnClickListener {
                applyFilter(categories)
            }
        }
        return view
    }

    private fun applyFilter(categories: List<CategoryModel>) {
        for (i in categories.indices) {
            val itemView = filterRecycler.findViewHolderForAdapterPosition(i)
                    as FilterCategoryAdapter.FilterCategoryHolder
            if (itemView.categorySwitch.isChecked) {
                itemView.category.categoryType?.let { it1 -> filteredCategories.add(it1) }
            }
        }
        newsFragmentViewModel.updateNews(filteredCategories)
        parentFragmentManager.popBackStack()
    }

    companion object {
        fun newInstance(): Fragment {
            return FilterFragment()
        }
    }
}