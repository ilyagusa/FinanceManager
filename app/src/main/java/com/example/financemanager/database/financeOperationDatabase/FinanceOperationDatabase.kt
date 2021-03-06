package com.example.financemanager.database.financeOperationDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FinanceOperation::class], version = 1, exportSchema = false)
abstract class FinanceOperationDatabase : RoomDatabase() {

    abstract fun getFinanceOperationDatabaseDao(): FinanceOperationDao

    companion object {
        @Volatile
        private var INSTANCE: FinanceOperationDatabase? = null

        fun getInstance(context: Context): FinanceOperationDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext,
                            FinanceOperationDatabase::class.java, "finance_operation_db")
                            .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}