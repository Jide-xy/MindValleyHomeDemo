package com.example.mindvalleytest.ui.main.newepisodes.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mindvalleytest.databinding.ItemLayoutNewEpisodesRvBinding
import com.example.mindvalleytest.room.entities.NewEpisode
import com.example.mindvalleytest.ui.main.ViewState

class NewEpisodesListAdapter : RecyclerView.Adapter<NewEpisodesListViewHolder>() {

    private var newEpisodes: List<NewEpisode> = emptyList()

    private var state: ViewState = ViewState.SUCCESS

    fun setLoading(state: ViewState) {
        this.state = state
        notifyItemChanged(0)
    }

    fun setEpisodes(newEpisodes: List<NewEpisode>) {
        state = ViewState.SUCCESS
        this.newEpisodes = newEpisodes
        notifyItemChanged(0)
    }

    fun setError(newEpisodes: List<NewEpisode>) {
        state = ViewState.ERROR
        this.newEpisodes = newEpisodes
        notifyItemChanged(0)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewEpisodesListViewHolder =
        NewEpisodesListViewHolder(
            ItemLayoutNewEpisodesRvBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun getItemCount(): Int = 1

    override fun onBindViewHolder(holder: NewEpisodesListViewHolder, position: Int) {
        holder.bind(newEpisodes, state)
    }
}