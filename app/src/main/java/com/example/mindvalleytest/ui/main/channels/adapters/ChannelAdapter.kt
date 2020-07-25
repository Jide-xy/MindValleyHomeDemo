package com.example.mindvalleytest.ui.main.channels.adapters

import android.content.Context
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.example.mindvalleytest.R
import com.example.mindvalleytest.core.room.models.ChannelsWithCoursesAndSeries
import com.example.mindvalleytest.databinding.ItemLayoutNewEpisodeBinding
import com.example.mindvalleytest.databinding.ItemLayoutSeriesBinding


class ChannelAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var channel: ChannelsWithCoursesAndSeries? = null

    fun setChannel(channelsWithCoursesAndSeries: com.example.mindvalleytest.core.room.models.ChannelsWithCoursesAndSeries) {
        channel = channelsWithCoursesAndSeries
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_layout_series ->
                SeriesViewHolder(
                    ItemLayoutSeriesBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            R.layout.item_layout_new_episode ->
                CourseViewHolder(
                    ItemLayoutNewEpisodeBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            else -> throw IllegalStateException("Unknown view type")
        }
    }

    override fun getItemCount(): Int {
        return if (channel?.isSeries() == true) channel?.series.orEmpty().size else channel?.courses.orEmpty().size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CourseViewHolder -> holder.bind(channel?.courses.orEmpty()[position])
            is SeriesViewHolder -> holder.bind(channel?.series.orEmpty()[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return channel?.let {
            when {
                it.isSeries() -> R.layout.item_layout_series
                else -> R.layout.item_layout_new_episode
            }
        } ?: throw IllegalStateException("No channel set on view")
    }

    inner class CourseViewHolder(private val binding: ItemLayoutNewEpisodeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(course: com.example.mindvalleytest.core.room.entities.Course) {
            binding.channelTitle.isVisible = false
            binding.episodeTitle.text = course.title
            binding.newEpisodeImageView.load(course.url)
        }
    }

    inner class SeriesViewHolder(private val binding: ItemLayoutSeriesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            val wm = binding.root.context.getSystemService(Context.WINDOW_SERVICE) as WindowManager

            val displayMetrics = DisplayMetrics()
            wm.defaultDisplay.getMetrics(displayMetrics)
            val screenWidth = displayMetrics.widthPixels
            val viewWidth = screenWidth * 0.8

            binding.root.apply {
                layoutParams = layoutParams.apply {
                    width = viewWidth.toInt()
                }
            }
        }

        fun bind(series: com.example.mindvalleytest.core.room.entities.Series) {
            binding.seriesTitle.text = series.title
            binding.seriesImageView.load(series.url)
        }

    }
}