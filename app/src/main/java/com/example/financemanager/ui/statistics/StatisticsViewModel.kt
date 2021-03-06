package com.example.financemanager.ui.statistics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StatisticsViewModel : ViewModel() {

    private var _text = MutableLiveData<String>("Statistics")
    val text: LiveData<String>
        get() = _text
}