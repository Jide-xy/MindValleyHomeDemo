package com.example.mindvalleytest.ui.main.newepisodes.adapters

import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.example.mindvalleytest.core.room.entities.NewEpisode
import com.example.mindvalleytest.databinding.ItemLayoutNewEpisodeBinding

class NewEpisodeViewHolder(private val binding: ItemLayoutNewEpisodeBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(newEpisode: NewEpisode) {
        binding.episodeTitle.text = newEpisode.title
        binding.channelTitle.text = newEpisode.channelTitle
        binding.newEpisodeImageView.load(newEpisode.url)
    }
}