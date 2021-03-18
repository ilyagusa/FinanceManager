package com.example.financemanager.ui.home.expensesCategory

import android.app.Application
import android.provider.ContactsContract
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.example.financemanager.database.expensesCategoryDatabase.ExpensesCategory
import com.example.financemanager.database.expensesCategoryDatabase.ExpensesCategoryDao
import com.example.financemanager.database.financeOperationDatabase.FinanceOperation
import com.example.financemanager.database.financeOperationDatabase.FinanceOperationDao
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class FinanceOperationViewModel(
    private val daoExpensesCategory: ExpensesCategoryDao,
    private val daoFinanceOperation: FinanceOperationDao,
    application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private var toExpenses = MutableLiveData<ExpensesCategory?>()
    private var toOperation = MutableLiveData<FinanceOperation>()

    val sumExpensesByMonth = daoFinanceOperation.getSumAmountByTypeAndMonth(
        SimpleDateFormat("MM").format(Date()),
        "Расходы"
    )
    val sumIncomeByMonth = daoFinanceOperation.getSumAmountByTypeAndMonth(
        SimpleDateFormat("MM").format(Date()),
        "Доходы"
    )
    val sumAllOperation = daoFinanceOperation.getSumByMonth(SimpleDateFormat("MM").format(Date()))

    init {
        initializeToExpenses()
        initializeToFinanceOperation()
    }


    private fun initializeToExpenses() {
        uiScope.launch {
            toExpenses.value = getToExpensesFromDatabase()
        }
    }

    private suspend fun getToExpensesFromDatabase(): ExpensesCategory? {
        return withContext(Dispatchers.IO) {
            var expenses = daoExpensesCategory.getToExpenses()
            expenses
        }
    }

    fun deleteCategoryByName(myCategory: String) {
        uiScope.launch {
            deleteExpensesCategory(myCategory)
            toExpenses.value = getToExpensesFromDatabase()
        }
    }

    private suspend fun deleteExpensesCategory(myCategory: String) {
        withContext(Dispatchers.IO) {
            daoExpensesCategory.deleteByCategory(myCategory)
        }
    }

    fun updateCategoryName(oldCategoryName: String, newCategoryName: String) {
        uiScope.launch {
            updateExpensesCategoryName(oldCategoryName, newCategoryName)
        }
    }

    private suspend fun updateExpensesCategoryName(
        oldCategoryName: String,
        newCategoryName: String
    ) {
        return withContext(Dispatchers.IO) {
            daoExpensesCategory.updateCategoryName(oldCategoryName, newCategoryName)
        }
    }

    //FinanceOperation

    private fun initializeToFinanceOperation() {
        uiScope.launch {
            toOperation.value = getToOperationFromDatabase()
        }
    }

    private suspend fun getToOperationFromDatabase(): FinanceOperation? {
        return withContext(Dispatchers.IO) {
            var operation = daoFinanceOperation.getToFinanceOperation()
            operation
        }
    }


    fun insertFinanceOperation(
        amount: Double,
        type: String,
        category: String,
        informationMessage: String
    ) {
        uiScope.launch {
            val financeOperation = FinanceOperation()
            financeOperation.amount = amount
            financeOperation.typeOperation = type
            financeOperation.categoryOperation = category
            financeOperation.informationMessage = informationMessage
            insert(financeOperation)
        }
    }


    private suspend fun insert(financeOperation: FinanceOperation) {
        withContext(Dispatchers.IO) {
            daoFinanceOperation.insert(financeOperation)
        }
    }

}