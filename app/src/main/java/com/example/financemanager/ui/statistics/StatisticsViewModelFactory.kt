package com.example.financemanager.ui.statistics

import com.example.financemanager.database.financeOperationDatabase.FinanceOperationDao

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class StatisticsViewModelFactory(
    private val dao: FinanceOperationDao,
    private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StatisticsViewModel::class.java)) {
            return StatisticsViewModel(dao, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}