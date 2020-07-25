package com.example.mindvalleytest.ui.main

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.zhuinden.livedatacombinetuplekt.combineTuple

class MainViewModel
@ViewModelInject constructor(
    repository: com.example.mindvalleytest.core.repository.IMindValleyRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _channelsLiveData = MutableLiveData<Unit>()
    val channelsLiveData = _channelsLiveData.switchMap {
        repository.getChannels().asLiveData()
    }

    private val _newEpisodesLiveData = MutableLiveData<Unit>()
    val newEpisodesLiveData = _newEpisodesLiveData.switchMap {
        repository.getNewEpisodes().asLiveData()
    }

    private val _categoriesLiveData = MutableLiveData<Unit>()
    val categoriesLiveData = _categoriesLiveData.switchMap {
        repository.getCategories().asLiveData()
    }

    val combinedStatusLiveData =
        combineTuple(channelsLiveData, newEpisodesLiveData, categoriesLiveData)

    init {
        refresh()
    }

    fun refresh() {
        _newEpisodesLiveData.value = Unit
        _channelsLiveData.value = Unit
        _categoriesLiveData.value = Unit
    }
}