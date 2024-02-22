package com.artem.android.task1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class HelpFragment: Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var bottomNavBarProfile: View
    private lateinit var bottomNavBarSearch: View

    private var categories: List<Category> = mutableListOf(
        Category("Дети", R.drawable.category_child),
        Category("Взрослые", R.drawable.category_adult),
        Category("Пожилые", R.drawable.category_old),
        Category("Животные", R.drawable.category_animals),
        Category("Мероприятия", R.drawable.category_events),
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.help_fragment, container, false)
        recyclerView = view.findViewById(R.id.help_category_rv)
        bottomNavigationView = view.findViewById(R.id.bottom_nav_bar_help)
        bottomNavigationView.selectedItemId = R.id.help
        bottomNavBarProfile = bottomNavigationView.findViewById(R.id.profile)
        bottomNavBarSearch = bottomNavigationView.findViewById(R.id.search)
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.adapter = CategoryAdapter(categories)
        return view
    }

    override fun onStart() {
        super.onStart()
        bottomNavBarProfile.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ProfileFragment.newInstance())
                .commit()
        }
        bottomNavBarSearch.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, SearchFragment.newInstance())
                .commit()
        }
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
        fun newInstance(): Fragment {
            return HelpFragment()
        }
    }
}