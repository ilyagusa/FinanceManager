package com.example.financemanager.ui.dialog

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
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
import com.example.financemanager.databinding.FragmentCreateExpenseBinding
import com.example.financemanager.ui.home.expensesCategory.ExpensesCategoryViewModel
import com.example.financemanager.ui.home.expensesCategory.ExpensesCategoryViewModelFactory
import kotlinx.android.synthetic.main.alert_empty_amount.*


class CreateExpensesDialog(item: ExpensesCategory) : DialogFragment() {

    private lateinit var viewModelExpenses: ExpensesCategoryViewModel
    private var item: ExpensesCategory = item

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentCreateExpenseBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_create_expense, container, false)
        val application = requireNotNull(this.activity).application
        val expensesCategoryDao = ExpensesCategoryDatabase.getInstance(application).getExpensesCategoryDatabaseDao()
        val daoFinanceOperationDao = FinanceOperationDatabase.getInstance(application).getFinanceOperationDatabaseDao()
        val viewModelFactoryExpensesCategory = ExpensesCategoryViewModelFactory(expensesCategoryDao, daoFinanceOperationDao, application)
        viewModelExpenses = ViewModelProvider(this, viewModelFactoryExpensesCategory).get(ExpensesCategoryViewModel::class.java)
        binding.editExpensesCategory.setText(item.categoryName)
        binding.buttonCancelExpenses.setOnClickListener() {
            dismiss()
        }

        binding.buttonAddExpenses.setOnClickListener() {
            var value: Double = 0.0
            var message: String = binding.editExpensesMessage.text.toString()
            var stringValue: String = binding.editExpensesAmount.text.toString()
            if (stringValue.isNotEmpty()){
                value = stringValue.toDouble()
                viewModelExpenses.insertFinanceOperation((-1)*value, "Расходы", item.categoryName, message)
                dismiss()
            }
            else {
                createAlertEmptyAmountDialog()
            }
        }

        binding.buttonCancelExpenses.setOnClickListener() {
            dismiss()
        }


        return binding.root
    }

    private fun createAlertEmptyAmountDialog() {
        val alertDialogEmptyAmount: Dialog = Dialog(this.context as Activity)
        alertDialogEmptyAmount.setContentView(R.layout.alert_empty_amount)
        alertDialogEmptyAmount.button_ok.setOnClickListener(){
            alertDialogEmptyAmount.dismiss()
        }
        alertDialogEmptyAmount.show()
    }

}