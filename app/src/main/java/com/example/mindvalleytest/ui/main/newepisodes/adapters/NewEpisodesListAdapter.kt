package com.example.mindvalleytest.ui.main.newepisodes.adapters

import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.mindvalleytest.R
import com.example.mindvalleytest.databinding.ItemLayoutNewEpisodesRvBinding
import com.example.mindvalleytest.databinding.ItemLayoutNewEpisodesRvShimmerBinding
import com.example.mindvalleytest.room.entities.NewEpisode
import com.example.mindvalleytest.ui.main.ViewState

class NewEpisodesListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var newEpisodes: List<NewEpisode> = emptyList()
    private var rvPosition: Parcelable? = null

    private var state: ViewState = ViewState.SUCCESS

    fun setLoading() {
        this.state = ViewState.LOADING
        notifyItemChanged(0)
    }

    fun setEpisodes(newEpisodes: List<NewEpisode>) {
        state = ViewState.SUCCESS
        this.newEpisodes = newEpisodes
        rvPosition = null
        notifyItemChanged(0)
    }

    fun setError(newEpisodes: List<NewEpisode>) {
        state = ViewState.ERROR
        this.newEpisodes = newEpisodes
        rvPosition = null
        notifyItemChanged(0)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            R.layout.item_layout_new_episodes_rv -> NewEpisodesListViewHolder(
                ItemLayoutNewEpisodesRvBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            R.layout.item_layout_new_episodes_rv_shimmer -> NewEpisodeShimmerViewHolder(
                ItemLayoutNewEpisodesRvShimmerBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            else -> throw IllegalStateException("Unknown view type")
        }

    override fun getItemCount(): Int = 1

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is NewEpisodesListViewHolder -> holder.bind(newEpisodes)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (state == ViewState.LOADING) R.layout.item_layout_new_episodes_rv_shimmer else R.layout.item_layout_new_episodes_rv
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        when (holder) {
            is NewEpisodesListViewHolder -> rvPosition = holder.getRecyclerViewSavedState()
        }
        super.onViewRecycled(holder)
    }

    inner class NewEpisodesListViewHolder(private val binding: ItemLayoutNewEpisodesRvBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val adapter = NewEpisodeAdapter()

        init {
            binding.newEpisodesRv.adapter = adapter
        }

        fun bind(newEpisodes: List<NewEpisode>) {
            binding.newEpisodesRv.isVisible = true
            adapter.setList(newEpisodes)
            rvPosition?.let {
                binding.newEpisodesRv.layoutManager?.onRestoreInstanceState(it)
            }
        }

        fun getRecyclerViewSavedState() = binding.newEpisodesRv.layoutManager?.onSaveInstanceState()

    }

    inner class NewEpisodeShimmerViewHolder(binding: ItemLayoutNewEpisodesRvShimmerBinding) :
        RecyclerView.ViewHolder(binding.root)
}