package com.example.financemanager.database.expensesCategoryDatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expenses_category")
data class ExpensesCategory(

        @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var categoryId: Long = 0L,

        @ColumnInfo(name = "category_name")
    var categoryName: String =  ""
)