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

    @Query("SELECT * FROM finance_operation ORDER BY id DESC")
    fun getAllFinanceOperation(): LiveData<List<FinanceOperation>>

    @Query("SELECT * FROM finance_operation ORDER BY id DESC LIMIT 1")
    fun getToFinanceOperation(): FinanceOperation?

}