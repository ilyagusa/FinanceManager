package com.example.financemanager.ui.dialog

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.financemanager.DecimalDigitsInputFilter
import com.example.financemanager.R
import com.example.financemanager.database.expensesCategoryDatabase.ExpensesCategory
import com.example.financemanager.database.expensesCategoryDatabase.ExpensesCategoryDatabase
import com.example.financemanager.database.financeOperationDatabase.FinanceOperationDatabase
import com.example.financemanager.databinding.FragmentCreateExpenseBinding
import com.example.financemanager.ui.home.expensesCategory.FinanceOperationViewModel
import com.example.financemanager.ui.home.expensesCategory.FinanceOperationViewModelFactory
import kotlinx.android.synthetic.main.alert_empty_amount.*


class CreateExpensesDialog() : DialogFragment() {

    private lateinit var viewModelExpenses: FinanceOperationViewModel

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
        val viewModelFactoryExpensesCategory = FinanceOperationViewModelFactory(expensesCategoryDao, daoFinanceOperationDao, application)
        viewModelExpenses = ViewModelProvider(this, viewModelFactoryExpensesCategory).get(FinanceOperationViewModel::class.java)

        val categoryNameArg = arguments?.getString("category_name")
        var categoryName = ""
        if (categoryNameArg != null){
            categoryName = categoryNameArg
        }

        binding.editExpensesCategory.setText(categoryName)
        binding.editExpensesAmount.filters = arrayOf<InputFilter>(DecimalDigitsInputFilter(10, 2))
        binding.buttonCancelExpenses.setOnClickListener() {
            dismiss()
        }

        binding.buttonAddExpenses.setOnClickListener() {
            var valueAmount: Double = 0.0
            var message: String = binding.editExpensesMessage.text.toString()
            var stringValueAmount: String = binding.editExpensesAmount.text.toString()
            if (stringValueAmount.isNotEmpty()){
                valueAmount = stringValueAmount.toDouble()
                viewModelExpenses.insertFinanceOperation((-1)*valueAmount, "Расходы", categoryName, message)
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
        val dialog = AlertEmptyAmountDialog()
        activity?.supportFragmentManager?.let { it1 -> dialog.show(it1, "alertEmptyAmountExpensesDialog") }
    }

}