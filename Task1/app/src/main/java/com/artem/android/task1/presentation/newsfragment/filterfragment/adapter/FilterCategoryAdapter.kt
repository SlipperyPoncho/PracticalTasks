package com.artem.android.task1.presentation.newsfragment.filterfragment.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.artem.android.task1.R
import com.artem.android.task1.domain.models.CategoryModel

class FilterCategoryAdapter(private var categories: List<CategoryModel>)
    : RecyclerView.Adapter<FilterCategoryAdapter.FilterCategoryHolder>() {

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    inner class FilterCategoryHolder(view: View): RecyclerView.ViewHolder(view) {

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterCategoryAdapter.FilterCategoryHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_item_filter_category, parent, false)
        return FilterCategoryHolder(view)
    }

    override fun onBindViewHolder(holder: FilterCategoryAdapter.FilterCategoryHolder, position: Int) {
        val category = categories[position]
        if (position == categories.size - 1) {
            holder.bindLast(category)
        } else {
            holder.bind(category)
        }
    }

    override fun getItemCount() = categories.size
}