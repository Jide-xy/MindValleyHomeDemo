package com.example.mindvalleytest.ui.main.newepisodes.adapters

import androidx.recyclerview.widget.RecyclerView
import com.example.mindvalleytest.core.room.entities.NewEpisode
import com.example.mindvalleytest.databinding.ItemLayoutNewEpisodeBinding
import com.example.mindvalleytest.util.imageloading.ImageLoader

class NewEpisodeViewHolder(
    private val binding: ItemLayoutNewEpisodeBinding,
    private val imageLoader: ImageLoader
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(newEpisode: NewEpisode) {
        binding.episodeTitle.text = newEpisode.title
        binding.channelTitle.text = newEpisode.channelTitle
        imageLoader.load(binding.newEpisodeImageView, newEpisode.url)
    }
}