package com.artem.android.task1.presentation.helpfragment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.artem.android.task1.R
import com.artem.android.task1.domain.models.CategoryModel
import com.artem.android.task1.data.getDrawableFromName

class CategoryAdapter(private val context: Context, private var categories: List<CategoryModel>)
    : RecyclerView.Adapter<CategoryAdapter.CategoryHolder>() {
    inner class CategoryHolder(view: View): RecyclerView.ViewHolder(view) {

        private val categoryTitle: TextView = itemView.findViewById(R.id.help_category_tv)
        private val categoryImg: ImageView = itemView.findViewById(R.id.help_category_iv)

        fun bind(category: CategoryModel) {
            categoryTitle.text = category.title
            if (category.image != null) {
                categoryImg.setImageDrawable(getDrawableFromName(context, category.image))
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapter.CategoryHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_item_help_category, parent, false)
        return CategoryHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryAdapter.CategoryHolder, position: Int) {
        val category = categories[position]
        holder.bind(category)
    }

    override fun getItemCount() = categories.size
}