package com.example.financemanager.ui.history

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.financemanager.database.expensesCategoryDatabase.ExpensesCategory
import com.example.financemanager.database.financeOperationDatabase.FinanceOperation
import com.example.financemanager.database.financeOperationDatabase.FinanceOperationDao
import kotlinx.coroutines.*

class HistoryViewModel(
        private val dao: FinanceOperationDao,
        application: Application) : AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main +  viewModelJob)

    private var toOperation = MutableLiveData<FinanceOperation?>()
    val operationList = dao.getAllFinanceOperation()

    private val _navigateToFinanceOperation = MutableLiveData<FinanceOperation>()
    val navigateToFinanceOperation: LiveData<FinanceOperation>
        get() = _navigateToFinanceOperation

    init {
        initializeToOperation()
    }

    private fun initializeToOperation() {
        uiScope.launch {
            toOperation.value = getToOperationFromDatabase()
        }
    }

    private suspend fun getToOperationFromDatabase(): FinanceOperation? {
        return withContext(Dispatchers.IO) {
            var operation = dao.getToFinanceOperation()
            operation
        }
    }


    fun insertOperation(categoryName : String) {
        uiScope.launch {
            val operation = FinanceOperation()
            operation.categoryOperation = categoryName
            operation.amount = 500.0
            operation.typeOperation = "Оплата"
            operation.informationMessage = "re"
            insert(operation)
            toOperation.value = getToOperationFromDatabase()
        }
    }


    private suspend fun insert(financeOperation: FinanceOperation) {
        withContext(Dispatchers.IO) {
            dao.insert(financeOperation)
        }
    }



    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}