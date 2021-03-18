package com.example.financemanager.database.financeOperationDatabase


import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FinanceOperationDao {

    @Insert
    fun insert(operation: FinanceOperation)

    @Update
    fun update(operation: FinanceOperation)

    @Delete
    fun delete(operation: FinanceOperation)

    @Query("SELECT * FROM finance_operation ORDER BY id DESC LIMIT 1")
    fun getToFinanceOperation(): FinanceOperation?

    @Query("SELECT * FROM finance_operation WHERE strftime('%m', date_operation) = :month ORDER BY id DESC")
    fun getAllFinanceOperation(month: String): LiveData<List<FinanceOperation>>

    @Query("SELECT  SUM(amount) FROM finance_operation WHERE strftime('%m', date_operation) = :month AND type = :type")
    fun getSumAmountByTypeAndMonth(month: String, type: String): LiveData<Double>?

    @Query("SELECT SUM(amount) FROM finance_operation WHERE strftime('%m', date_operation) = :month")
    fun getSumByMonth(month: String): LiveData<Double>?

    //Получени баланса на текущий день
    @Query("SELECT SUM(amount) FROM finance_operation WHERE  strftime('%dd', date_operation)  <=  :date")
    fun getSumBeforeDay(date: String): Double?

    @Query("DELETE FROM finance_operation WHERE id = :id")
    fun deleteFinanceOperationById(id: Long)
}