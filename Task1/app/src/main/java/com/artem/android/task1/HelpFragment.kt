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
import com.artem.android.task1.domain.CategoryModel


class HelpFragment: Fragment() {

    private lateinit var recyclerView: RecyclerView

    private val charitySharedViewModel by lazy {
        ViewModelProvider(requireActivity())[CharitySharedViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_help, container, false)
        recyclerView = view.findViewById(R.id.help_category_rv)
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        charitySharedViewModel.categories.observe(viewLifecycleOwner) {
            if (it != null) {
                recyclerView.adapter = CategoryAdapter(it)
            }
        }
        return view
    }

    private inner class CategoryHolder(view: View): RecyclerView.ViewHolder(view) {

        private val categoryTitle: TextView = itemView.findViewById(R.id.help_category_tv)
        private val categoryImg: ImageView = itemView.findViewById(R.id.help_category_iv)

        fun bind(category: CategoryModel) {
            categoryTitle.text = category.title
            if (category.image != null) {
                categoryImg.setImageDrawable(getDrawableFromName(requireContext(), category.image))
            }
        }
    }

    private inner class CategoryAdapter(private var categories: List<CategoryModel>)
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