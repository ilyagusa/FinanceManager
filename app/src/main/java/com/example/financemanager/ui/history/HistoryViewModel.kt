package com.example.financemanager.ui.history

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.financemanager.database.expensesCategoryDatabase.ExpensesCategory
import com.example.financemanager.database.financeOperationDatabase.FinanceOperation
import com.example.financemanager.database.financeOperationDatabase.FinanceOperationDao
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*

class HistoryViewModel(
        private val dao: FinanceOperationDao,
        application: Application) : AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main +  viewModelJob)

    private var toOperation = MutableLiveData<FinanceOperation?>()

    val operationList = dao.getAllFinanceOperation(SimpleDateFormat("MM").format(Date()))

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

    fun deleteFinanceOperation(operation: FinanceOperation) {
        uiScope.launch {
            delete(operation)
        }
    }

    private suspend fun delete(operation: FinanceOperation) {
        return withContext(Dispatchers.IO) {
            dao.delete(operation)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}