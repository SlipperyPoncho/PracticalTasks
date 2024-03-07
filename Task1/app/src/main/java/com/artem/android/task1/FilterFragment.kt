package com.artem.android.task1

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class FilterFragment: Fragment() {
    private lateinit var filterRecycler: RecyclerView
    private lateinit var filterOK: ImageView
    private val categories = HelpFragment.categories
    private lateinit var oldEvents: List<Event>
    private lateinit var newEvents: List<Event>
    private var filteredCategories: MutableList<CategoryType> = mutableListOf()

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        oldEvents = arguments?.getParcelableArrayList(ARG_EVENT, Event::class.java)?.toList()
            ?: mutableListOf()
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
        filterRecycler.adapter = FilterCategoryAdapter(categories)
        return view
    }

    override fun onStart() {
        super.onStart()
        filterOK.setOnClickListener {
            for (i in categories.indices) {
                val itemView = filterRecycler.findViewHolderForAdapterPosition(i) as FilterCategoryHolder
                if (itemView.categorySwitch.isChecked) {
                    itemView.category.categoryType?.let { it1 -> filteredCategories.add(it1) }
                }
            }
            newEvents = oldEvents.filter { it.categories.containsAll(filteredCategories) }
            val result = Bundle().apply {
                putParcelableArrayList("listBundleKey", ArrayList(newEvents))
            }
            parentFragmentManager.setFragmentResult("listRequestKey", result)
            parentFragmentManager.popBackStack()
        }
    }

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private inner class FilterCategoryHolder(view: View): ViewHolder(view) {

        lateinit var category: Category
        private val categoryTitle: TextView = itemView.findViewById(R.id.help_category_filter_item_tv)
        private val categoryUnderline: View = itemView.findViewById(R.id.filter_underline)
        var categorySwitch: Switch = itemView.findViewById(R.id.category_switch)

        fun bind(category: Category) {
            this.category = category
            categoryTitle.text = category.title
        }

        fun bindLast(category: Category) {
            this.category = category
            categoryTitle.text = category.title
            categoryUnderline.visibility = View.GONE
        }
    }

    private inner class FilterCategoryAdapter(private var categories: List<Category>)
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
        private const val ARG_EVENT = "event"
        fun newInstance(events: List<Event>): Fragment {
            val args = Bundle().apply { putParcelableArrayList(ARG_EVENT, ArrayList(events)) }
            return FilterFragment().apply { arguments = args }
        }
    }
}