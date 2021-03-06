package com.example.financemanager.ui.home.expensesCategory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.financemanager.database.expensesCategoryDatabase.ExpensesCategoryDao
import com.example.financemanager.database.financeOperationDatabase.FinanceOperationDao

class FinanceOperationViewModelFactory(
        private val daoExpensesCategory: ExpensesCategoryDao,
        private val daoFinanceOperation: FinanceOperationDao,
        private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FinanceOperationViewModel::class.java)) {
            return FinanceOperationViewModel(daoExpensesCategory, daoFinanceOperation, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}