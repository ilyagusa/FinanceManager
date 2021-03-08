package com.example.financemanager.database.expensesCategoryDatabase

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ExpensesCategoryDao {

    @Insert
    fun insert(button: ExpensesCategory)

    @Update
    fun update(button: ExpensesCategory)

    @Delete
    fun delete(button: ExpensesCategory)

    @Query("SELECT * FROM expenses_category WHERE id = :key")
    fun get(key: Long): ExpensesCategory?

    @Query("DELETE FROM expenses_category")
    fun clear()

    @Query( "DELETE FROM expenses_category WHERE category_name = :my_category")
    fun deleteByCategory(my_category: String)

    @Query("UPDATE expenses_category SET category_name = :new_category_name WHERE category_name = :old_category_name")
    fun updateCategoryName(old_category_name: String, new_category_name: String)

    @Query("SELECT * FROM expenses_category ORDER BY id DESC")
    fun getAllExpenses(): LiveData<List<ExpensesCategory>>

    @Query("SELECT * FROM expenses_category ORDER BY id DESC LIMIT 1")
    fun getToExpenses(): ExpensesCategory?

    @Query("SELECT * FROM expenses_category WHERE category_name = :name")
    fun isRowIsExist(name : String) : ExpensesCategory?

}