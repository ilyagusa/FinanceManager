package com.example.financemanager.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.financemanager.database.expensesCategoryDatabase.ExpensesCategory
import com.example.financemanager.database.expensesCategoryDatabase.ExpensesCategoryDao
import kotlinx.coroutines.*

class HomeViewModel(
        private val dao: ExpensesCategoryDao,
        application: Application) : AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main +  viewModelJob)


    private var toExpenses = MutableLiveData<ExpensesCategory?>()

    val expensesList = dao.getAllExpenses()

    private val _navigateToExpensesCategory = MutableLiveData<ExpensesCategory>()
    val navigateToExpensesCategory: LiveData<ExpensesCategory>
        get() = _navigateToExpensesCategory

    init {
        initializeToExpenses()
    }

    private fun initializeToExpenses() {
        uiScope.launch {
            toExpenses.value = getToExpensesFromDatabase()
        }
    }

    private suspend fun getToExpensesFromDatabase(): ExpensesCategory? {
        return withContext(Dispatchers.IO) {
            var expenses = dao.getToExpenses()
            expenses
        }
    }

    fun insertButton(categoryName : String) {
        uiScope.launch {
            val newExpenses = ExpensesCategory()
            newExpenses.categoryName = categoryName
            insert(newExpenses)
            toExpenses.value = getToExpensesFromDatabase()
        }
    }


    private suspend fun insert(expensesCategory: ExpensesCategory) {
        withContext(Dispatchers.IO) {
            dao.insert(expensesCategory)
        }
    }

    fun onClear() {
        uiScope.launch {
            clear()
            toExpenses.value = null
        }
    }

    private suspend fun clear() {
        withContext(Dispatchers.IO) {
            dao.clear()
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private var _text = MutableLiveData<String>("100000")
    val text: LiveData<String>
        get() = _text

}