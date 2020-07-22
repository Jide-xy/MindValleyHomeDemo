package com.example.mindvalleytest.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import com.example.mindvalleytest.repository.IMindValleyRepository

class MainViewModel @ViewModelInject constructor(repository: IMindValleyRepository) : ViewModel() {

    private val _channelsLiveData = MutableLiveData<Unit>()
    val channelsLiveData = _channelsLiveData.switchMap {
        repository.getChannels().asLiveData()
    }
}