package com.example.financemanager.ui.statistics

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.example.financemanager.database.financeOperationDatabase.FinanceOperationDao
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*

class StatisticsViewModel(
    private val daoFinanceOperation: FinanceOperationDao,
    application: Application
) : AndroidViewModel(application) {


    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    var listBalanceOfMonth = MutableLiveData<MutableList<DataEntry>>()

    init {
        listBalanceOfMonth.value = arrayListOf()
        getListBalanceDay()
    }


    private fun getListBalanceDay() {
        uiScope.launch {
            val dataBalance: MutableList<DataEntry> = arrayListOf()
            val today = SimpleDateFormat("dd").format(Date())
            for (i in 1..today.toInt()) {
                var day = "${i + 1}"
                if (i < 10) {
                    day = "0${i + 1}"
                }
                val balanceBeforeDay = getBalanceBeforeDay(day)
                var dayDataEntry: DataEntry
                if (balanceBeforeDay != null) {
                    dayDataEntry = ValueDataEntry("${i}", balanceBeforeDay)
                } else dayDataEntry = ValueDataEntry("${i}", 0.00)
                dataBalance.add(dayDataEntry)
            }
            listBalanceOfMonth.value = dataBalance
        }
    }


    private suspend fun getBalanceBeforeDay(day: String): Double? {
        return withContext(Dispatchers.IO) {
            val sum = daoFinanceOperation.getSumBeforeDay(day)
            sum
        }
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}