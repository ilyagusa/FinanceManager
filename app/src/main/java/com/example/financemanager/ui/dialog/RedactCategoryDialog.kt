package com.example.financemanager.ui.dialog

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.financemanager.R
import com.example.financemanager.database.expensesCategoryDatabase.ExpensesCategory
import com.example.financemanager.database.expensesCategoryDatabase.ExpensesCategoryDatabase
import com.example.financemanager.database.financeOperationDatabase.FinanceOperationDatabase
import com.example.financemanager.databinding.FragmentEditCategoryNameBinding
import com.example.financemanager.ui.home.expensesCategory.FinanceOperationViewModel
import com.example.financemanager.ui.home.expensesCategory.FinanceOperationViewModelFactory
import kotlinx.android.synthetic.main.alert_empty_category.*


class RedactCategoryDialog(item: ExpensesCategory) : DialogFragment() {

    private lateinit var viewModelExpenses: FinanceOperationViewModel
    private var item: ExpensesCategory = item

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentEditCategoryNameBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_edit_category_name, container, false)
        val application = requireNotNull(this.activity).application
        val expensesCategoryDao = ExpensesCategoryDatabase.getInstance(application).getExpensesCategoryDatabaseDao()
        val daoFinanceOperationDao = FinanceOperationDatabase.getInstance(application).getFinanceOperationDatabaseDao()
        val viewModelFactoryExpensesCategory = FinanceOperationViewModelFactory(expensesCategoryDao, daoFinanceOperationDao, application)
        viewModelExpenses = ViewModelProvider(this, viewModelFactoryExpensesCategory).get(FinanceOperationViewModel::class.java)
        binding.textCategoryChange.movementMethod = ScrollingMovementMethod()
        val str: String = resources.getString(R.string.text_change_name_category) + " '" + item.categoryName + "'"
        binding.textCategoryChange.text = str
        val oldCategoryName = item.categoryName
        binding.buttonSubmitNameCategory.setOnClickListener() {
            if (binding.editNameCategory.text.isNotEmpty()) {
                viewModelExpenses.updateCategoryName(oldCategoryName, binding.editNameCategory.text.toString())
                dismiss()
            } else createAlertEmptyCategoryNameDialog()
        }
        return binding.root
    }

    private fun createAlertEmptyCategoryNameDialog() {
        val alertDialogEmptyAmount: Dialog = Dialog(this.context as Activity)
        alertDialogEmptyAmount.setContentView(R.layout.alert_empty_category)
        alertDialogEmptyAmount.button_ok2.setOnClickListener() {
            alertDialogEmptyAmount.dismiss()
        }
        alertDialogEmptyAmount.show()
    }
}