package com.example.mindvalleytest.ui.main.channels.adapters

import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.transform.CircleCropTransformation
import com.example.mindvalleytest.R
import com.example.mindvalleytest.databinding.ItemLayoutChannelListBinding
import com.example.mindvalleytest.databinding.ItemLayoutCourseListShimmerBinding
import com.example.mindvalleytest.databinding.ItemLayoutSeriesListShimmerBinding
import com.example.mindvalleytest.room.models.ChannelsWithCoursesAndSeries
import com.example.mindvalleytest.ui.main.ViewState

class ChannelListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<ChannelsWithCoursesAndSeries> = emptyList()
    private var rvPosition = mutableListOf<Parcelable?>()

    private var state: ViewState = ViewState.SUCCESS

    fun setLoading() {
        this.state = ViewState.LOADING
        rvPosition = MutableList(2) { null }
        notifyDataSetChanged()
    }

    fun setEpisodes(items: List<ChannelsWithCoursesAndSeries>) {
        state = ViewState.SUCCESS
        this.items = items
        rvPosition = MutableList(items.size) { null }
        notifyDataSetChanged()
    }

    fun setError(items: List<ChannelsWithCoursesAndSeries>) {
        state = ViewState.ERROR
        this.items = items
        rvPosition = MutableList(items.size) { null }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_layout_course_list_shimmer ->
                CourseShimmerViewHolder(
                    ItemLayoutCourseListShimmerBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            R.layout.item_layout_series_list_shimmer ->
                SeriesShimmerViewHolder(
                    ItemLayoutSeriesListShimmerBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            else -> ChannelListViewHolder(
                ItemLayoutChannelListBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        }
    }

    override fun getItemCount(): Int {
        return when {
            state == ViewState.LOADING && items.isEmpty() -> 2
            else -> items.size
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ChannelListViewHolder -> holder.bind(items[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (state) {
            ViewState.LOADING -> if (position == 0) R.layout.item_layout_course_list_shimmer else R.layout.item_layout_series_list_shimmer
            else -> R.layout.item_layout_channel_list
        }
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        when {
            holder is ChannelListViewHolder && holder.bindingAdapterPosition != RecyclerView.NO_POSITION -> rvPosition[holder.bindingAdapterPosition] =
                holder.getRecyclerViewSavedState()
        }
        super.onViewRecycled(holder)
    }

    inner class ChannelListViewHolder(private val binding: ItemLayoutChannelListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val adapter = ChannelAdapter()

        init {
            binding.newEpisodesRv.adapter = adapter
        }

        fun bind(channelWithCoursesAndSeries: ChannelsWithCoursesAndSeries) {
            binding.channelTitle.text = channelWithCoursesAndSeries.channel.title
            binding.count.text = if (channelWithCoursesAndSeries.isSeries()) {
                "${channelWithCoursesAndSeries.series.size} series"
            } else "${channelWithCoursesAndSeries.courses.size} episodes"
            binding.channelIcon.load(
                channelWithCoursesAndSeries.channel.iconAssetUrl
                    ?: channelWithCoursesAndSeries.channel.coverAssetUrl
            ) {
                crossfade(true)
                placeholder(R.drawable.channel_icon_placeholder)
                transformations(CircleCropTransformation())
            }
            adapter.setChannel(channelWithCoursesAndSeries)
            rvPosition[bindingAdapterPosition]?.let {
                binding.newEpisodesRv.layoutManager?.onRestoreInstanceState(it)
            }
        }

        fun getRecyclerViewSavedState() = binding.newEpisodesRv.layoutManager?.onSaveInstanceState()
    }

    inner class CourseShimmerViewHolder(binding: ItemLayoutCourseListShimmerBinding) :
        RecyclerView.ViewHolder(binding.root)

    inner class SeriesShimmerViewHolder(binding: ItemLayoutSeriesListShimmerBinding) :
        RecyclerView.ViewHolder(binding.root)
}