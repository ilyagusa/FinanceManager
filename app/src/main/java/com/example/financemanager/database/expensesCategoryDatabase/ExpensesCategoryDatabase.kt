package com.example.financemanager.database.expensesCategoryDatabase


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ExpensesCategory::class], version = 1, exportSchema = false)
abstract class ExpensesCategoryDatabase : RoomDatabase() {

    abstract fun getExpensesCategoryDatabaseDao(): ExpensesCategoryDao

    companion object {
        @Volatile
        private var INSTANCE: ExpensesCategoryDatabase? = null

        fun getInstance(context: Context): ExpensesCategoryDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext,
                        ExpensesCategoryDatabase::class.java, "expenses_category_db")
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}