package com.example.financemanager.database.expensesCategoryDatabase


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.financemanager.database.financeOperationDatabase.FinanceOperation
import com.example.financemanager.database.financeOperationDatabase.FinanceOperationDao

@Database(entities = [ExpensesCategory::class, FinanceOperation::class], version = 1, exportSchema = false)
abstract class ExpensesCategoryDatabase : RoomDatabase() {

    abstract fun getExpensesCategoryDatabaseDao(): ExpensesCategoryDao
    abstract fun getFinanceOperationDatabaseDao(): FinanceOperationDao

    companion object {
        @Volatile
        private var INSTANCE: ExpensesCategoryDatabase? = null

        fun getInstance(context: Context): ExpensesCategoryDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext,
                        ExpensesCategoryDatabase::class.java, "main_database")
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}