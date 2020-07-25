package com.example.mindvalleytest.ui.main

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.mindvalleytest.core.util.NetworkStatus
import com.example.mindvalleytest.databinding.MainFragmentBinding
import com.example.mindvalleytest.ui.main.categories.adapters.CategoryListAdapter
import com.example.mindvalleytest.ui.main.channels.adapters.ChannelListAdapter
import com.example.mindvalleytest.ui.main.newepisodes.adapters.NewEpisodesListAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding: MainFragmentBinding? = null

    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!

    private val newEpisodeListAdapter = NewEpisodesListAdapter()
    private val channelListAdapter = ChannelListAdapter()
    private val categoryListAdapter = CategoryListAdapter()

    private val concatAdapter = ConcatAdapter(
        HeaderAdapter(),
        newEpisodeListAdapter,
        channelListAdapter,
        categoryListAdapter
    )

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.swipeLayout.setOnRefreshListener {
            viewModel.refresh()
        }
        binding.mainRv.addItemDecoration(
            DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL).apply {
                setDrawable(ColorDrawable(Color.parseColor("#3C434E")))
            }
        )
        binding.mainRv.adapter = concatAdapter
        setUpObservers()
    }

    private fun setUpObservers() {
        viewModel.channelsLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is NetworkStatus.Success -> {
                    channelListAdapter.setEpisodes(it.data)
                }
                is NetworkStatus.Loading -> {
                    if (it.data.isNullOrEmpty()) {
                        channelListAdapter.setLoading()
                    } else {
                        channelListAdapter.setEpisodes(it.data!!)
                    }
                }
                is NetworkStatus.Error -> {
                    channelListAdapter.setError(it.data.orEmpty())
                    showError(it.message)
                }
            }
        })

        viewModel.newEpisodesLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is NetworkStatus.Success -> {
                    newEpisodeListAdapter.setEpisodes(it.data)
                }
                is NetworkStatus.Loading -> {
                    if (it.data.isNullOrEmpty()) {
                        newEpisodeListAdapter.setLoading()
                    } else {
                        newEpisodeListAdapter.setEpisodes(it.data!!)
                    }
                }
                is NetworkStatus.Error -> {
                    newEpisodeListAdapter.setError(it.data.orEmpty())
                    showError(it.message)
                }
            }
        })

        viewModel.categoriesLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is NetworkStatus.Success -> {
                    categoryListAdapter.setList(it.data)
                }
                is NetworkStatus.Loading -> {
                    if (it.data.isNullOrEmpty()) {
                        categoryListAdapter.setLoading()
                    } else {
                        categoryListAdapter.setList(it.data!!)
                    }
                }
                is NetworkStatus.Error -> {
                    categoryListAdapter.setError(it.data.orEmpty())
                    showError(it.message)
                }
            }
        })


        viewModel.combinedStatusLiveData.observe(viewLifecycleOwner, Observer {
            when {
                (it.first is NetworkStatus.Loading)
                        || it.second is NetworkStatus.Loading
                        || it.first is NetworkStatus.Loading
                -> {
                    binding.swipeLayout.isRefreshing = true
                }
                else -> binding.swipeLayout.isRefreshing = false
            }
        })
    }

    private fun showError(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}