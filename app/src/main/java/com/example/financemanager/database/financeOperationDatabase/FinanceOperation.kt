package com.example.financemanager.database.financeOperationDatabase


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "finance_operation")
data class FinanceOperation(

        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        var operationId: Long = 0L,

        @ColumnInfo(name = "date_operation")
        val dateOperation: String =  SimpleDateFormat("yyyy-MM-dd").format(Date()),

        @ColumnInfo(name = "amount")
        var amount: Double = 0.0,

        @ColumnInfo(name = "type")
        var typeOperation: String = "",

        @ColumnInfo(name = "category")
        var categoryOperation: String = "",

        @ColumnInfo(name = "information_message")
        var informationMessage: String = ""
)