package com.example.mindvalleytest.ui.main.newepisodes.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mindvalleytest.core.room.entities.NewEpisode
import com.example.mindvalleytest.databinding.ItemLayoutNewEpisodeBinding

class NewEpisodeAdapter : RecyclerView.Adapter<NewEpisodeViewHolder>() {

    private var newEpisodes = emptyList<NewEpisode>()

    fun setList(newEpisodes: List<NewEpisode>) {
        this.newEpisodes = newEpisodes
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewEpisodeViewHolder =
        NewEpisodeViewHolder(
            ItemLayoutNewEpisodeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = newEpisodes.size

    override fun onBindViewHolder(holder: NewEpisodeViewHolder, position: Int) {
        holder.bind(newEpisodes[position])
    }
}