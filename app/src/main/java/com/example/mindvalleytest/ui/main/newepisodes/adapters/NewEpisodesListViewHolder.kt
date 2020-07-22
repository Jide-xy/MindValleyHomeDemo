package com.example.mindvalleytest.ui.main.newepisodes.adapters

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.mindvalleytest.databinding.ItemLayoutNewEpisodesRvBinding
import com.example.mindvalleytest.room.entities.NewEpisode
import com.example.mindvalleytest.ui.main.ViewState

class NewEpisodesListViewHolder(private val binding: ItemLayoutNewEpisodesRvBinding) :
    RecyclerView.ViewHolder(binding.root) {

    private val adapter = NewEpisodeAdapter()

    fun bind(newEpisodes: List<NewEpisode>, state: ViewState) {
        when (state) {
            ViewState.LOADING -> {
                binding.newEpisodesRv.isVisible = false
                binding.shimmerLayout.isVisible = true
                binding.shimmerLayout.startShimmer()
            }
            ViewState.SUCCESS -> {
                binding.shimmerLayout.stopShimmer()
                binding.shimmerLayout.isVisible = false
                binding.newEpisodesRv.isVisible = true
                adapter.setList(newEpisodes)
                binding.newEpisodesRv.adapter = adapter
            }
            ViewState.ERROR -> {

            }
        }
    }
}