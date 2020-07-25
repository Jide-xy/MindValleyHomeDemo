package com.example.mindvalleytest.ui.main.categories.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mindvalleytest.R
import com.example.mindvalleytest.core.room.entities.Category
import com.example.mindvalleytest.databinding.ItemLayoutCategoriesListBinding
import com.example.mindvalleytest.databinding.ItemLayoutCategoryListShimmerBinding
import com.example.mindvalleytest.ui.main.ViewState
import com.example.mindvalleytest.util.GridSpacingItemDecoration
import com.example.mindvalleytest.util.ViewUtils.dpToPx

class CategoryListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<Category> = emptyList()

    private var state: ViewState = ViewState.SUCCESS

    fun setLoading() {
        this.state = ViewState.LOADING
        notifyItemChanged(0)
    }

    fun setList(items: List<Category>) {
        state = ViewState.SUCCESS
        this.items = items
        notifyItemChanged(0)
    }

    fun setError(items: List<Category>) {
        state = ViewState.ERROR
        this.items = items
        notifyItemChanged(0)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_layout_category_list_shimmer -> CategoryListShimmerViewHolder(
                ItemLayoutCategoryListShimmerBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            R.layout.item_layout_categories_list -> CategoryListViewHolder(
                ItemLayoutCategoriesListBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            else -> throw IllegalStateException("Unknown view type")
        }
    }

    override fun getItemCount() = 1

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CategoryListViewHolder -> holder.bind(items)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (state) {
            ViewState.LOADING -> R.layout.item_layout_category_list_shimmer
            else -> R.layout.item_layout_categories_list
        }
    }

    inner class CategoryListViewHolder(binding: ItemLayoutCategoriesListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val adapter = CategoryAdapter()

        init {
            binding.categoriesRv.addItemDecoration(
                GridSpacingItemDecoration(2, dpToPx(binding.root.context, 15).toInt(), false)
            )
            binding.categoriesRv.isNestedScrollingEnabled = false
            binding.categoriesRv.adapter = adapter
        }

        fun bind(items: List<Category>) {
            adapter.setList(items)
        }
    }

    inner class CategoryListShimmerViewHolder(binding: ItemLayoutCategoryListShimmerBinding) :
        RecyclerView.ViewHolder(binding.root)
}