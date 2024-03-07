package com.artem.android.task1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HelpFragment: Fragment() {

    private lateinit var recyclerView: RecyclerView

    private val categoryViewModel by lazy {
        ViewModelProvider(this)[CategoryViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val categoriesFromJson = context?.let { categoryViewModel.categoryFromJSON(it) }
        if (categoriesFromJson != null) {
            categories = categoriesFromJson
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_help, container, false)
        recyclerView = view.findViewById(R.id.help_category_rv)
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.adapter = CategoryAdapter(categories)
        return view
    }

    private inner class CategoryHolder(view: View): RecyclerView.ViewHolder(view) {

        private lateinit var category: Category
        private val categoryTitle: TextView = itemView.findViewById(R.id.help_category_tv)
        private val categoryImg: ImageView = itemView.findViewById(R.id.help_category_iv)

        fun bind(category: Category) {
            this.category = category
            categoryTitle.text = category.title
            category.imageResId?.let { categoryImg.setImageResource(it) }
        }
    }

    private inner class CategoryAdapter(private var categories: List<Category>)
        : RecyclerView.Adapter<CategoryHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.list_item_help_category, parent, false)
            return CategoryHolder(view)
        }

        override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
            val category = categories[position]
            holder.bind(category)
        }

        override fun getItemCount() = categories.size
    }

    companion object {
        var categories: List<Category> = mutableListOf()
        fun newInstance(): Fragment {
            return HelpFragment()
        }
    }
}