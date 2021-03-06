package com.example.financemanager.ui.history

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.financemanager.database.financeOperationDatabase.FinanceOperationDao


class HistoryViewModelFactory(
        private val daoFinanceOperation: FinanceOperationDao,
        private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HistoryViewModel::class.java)) {
            return HistoryViewModel(daoFinanceOperation, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}