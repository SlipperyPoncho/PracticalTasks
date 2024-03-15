package com.artem.android.task1

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class FilterFragment: Fragment() {
    private lateinit var filterRecycler: RecyclerView
    private lateinit var filterOK: ImageView
    private var filteredCategories: MutableList<CategoryType> = mutableListOf()

    private val charitySharedViewModel by lazy {
        ViewModelProvider(requireActivity())[CharitySharedViewModel::class.java]
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
        charitySharedViewModel.categories.observe(viewLifecycleOwner) { categories ->
            filterRecycler.adapter = FilterCategoryAdapter(categories)
            filterOK.setOnClickListener {
                applyFilter(categories)
            }
        }
        return view
    }

    private fun applyFilter(categories: List<CategoryModel>) {
        for (i in categories.indices) {
            val itemView = filterRecycler.findViewHolderForAdapterPosition(i) as FilterCategoryHolder
            if (itemView.categorySwitch.isChecked) {
                itemView.category.categoryType?.let { it1 -> filteredCategories.add(it1) }
            }
        }
        charitySharedViewModel.updateEvents(filteredCategories)
        parentFragmentManager.popBackStack()
    }


    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private inner class FilterCategoryHolder(view: View): ViewHolder(view) {

        lateinit var category: CategoryModel
        private val categoryTitle: TextView = itemView.findViewById(R.id.help_category_filter_item_tv)
        private val categoryUnderline: View = itemView.findViewById(R.id.filter_underline)
        var categorySwitch: Switch = itemView.findViewById(R.id.category_switch)

        fun bind(category: CategoryModel) {
            this.category = category
            categoryTitle.text = category.title
            categorySwitch.isChecked = category.isChecked
        }

        fun bindLast(category: CategoryModel) {
            this.category = category
            categoryTitle.text = category.title
            categorySwitch.isChecked = category.isChecked
            categoryUnderline.visibility = View.GONE
        }
    }

    private inner class FilterCategoryAdapter(private var categories: List<CategoryModel>)
        : RecyclerView.Adapter<FilterCategoryHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterCategoryHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.list_item_filter_category, parent, false)
            return FilterCategoryHolder(view)
        }

        override fun onBindViewHolder(holder: FilterCategoryHolder, position: Int) {
            val category = categories[position]
            if (position == categories.size - 1) {
                holder.bindLast(category)
            } else {
                holder.bind(category)
            }
        }

        override fun getItemCount() = categories.size
    }

    companion object {
        fun newInstance(): Fragment {
            return FilterFragment()
        }
    }
}