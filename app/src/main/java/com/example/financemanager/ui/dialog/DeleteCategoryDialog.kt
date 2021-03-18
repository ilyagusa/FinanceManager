package com.example.financemanager.ui.dialog

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.financemanager.R
import com.example.financemanager.database.expensesCategoryDatabase.ExpensesCategory
import com.example.financemanager.database.expensesCategoryDatabase.ExpensesCategoryDatabase
import com.example.financemanager.database.financeOperationDatabase.FinanceOperationDatabase
import com.example.financemanager.databinding.FragmentChooseDeleteDialogBinding
import com.example.financemanager.ui.home.expensesCategory.FinanceOperationViewModel
import com.example.financemanager.ui.home.expensesCategory.FinanceOperationViewModelFactory


class DeleteCategoryDialog() : DialogFragment() {

    private lateinit var viewModelExpenses: FinanceOperationViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentChooseDeleteDialogBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_choose_delete_dialog, container, false)
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

        binding.textViewChooseDelete.movementMethod = ScrollingMovementMethod()
        val str: String = resources.getString(R.string.choose_delete_dialog) + " '" + categoryName + "' ?"
        binding.textViewChooseDelete.text = str
        binding.submitButton.setOnClickListener() {
            viewModelExpenses.deleteCategoryByName(categoryName)
            dismiss()
            Toast.makeText(context, "Категория: ${categoryName} удалена", Toast.LENGTH_SHORT).show()
        }
        binding.cancelButton.setOnClickListener() {
            dismiss()
        }
        return binding.root
    }
}