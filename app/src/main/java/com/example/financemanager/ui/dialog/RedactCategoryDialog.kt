package com.example.financemanager.ui.dialog

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.financemanager.R
import com.example.financemanager.database.expensesCategoryDatabase.ExpensesCategoryDatabase
import com.example.financemanager.databinding.FragmentEditCategoryNameBinding
import com.example.financemanager.ui.home.expensesCategory.FinanceOperationViewModel
import com.example.financemanager.ui.home.expensesCategory.FinanceOperationViewModelFactory


class RedactCategoryDialog() : DialogFragment() {

    private lateinit var viewModelExpenses: FinanceOperationViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentEditCategoryNameBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_edit_category_name, container, false
        )
        val application = requireNotNull(this.activity).application
        val expensesCategoryDao =
            ExpensesCategoryDatabase.getInstance(application).getExpensesCategoryDatabaseDao()
        val daoFinanceOperationDao =
            ExpensesCategoryDatabase.getInstance(application).getFinanceOperationDatabaseDao()
        val viewModelFactoryExpensesCategory = FinanceOperationViewModelFactory(
            expensesCategoryDao,
            daoFinanceOperationDao,
            application
        )
        viewModelExpenses = ViewModelProvider(
            this,
            viewModelFactoryExpensesCategory
        ).get(FinanceOperationViewModel::class.java)

        val categoryNameArg = arguments?.getString("category_name")
        var categoryName = ""
        if (categoryNameArg != null) {
            categoryName = categoryNameArg
        }

        binding.textCategoryChange.movementMethod = ScrollingMovementMethod()
        val str: String =
            resources.getString(R.string.text_change_name_category) + " '" + categoryName + "'"
        binding.textCategoryChange.text = str
        val oldCategoryName = categoryName
        binding.buttonSubmitNameCategory.setOnClickListener() {
            if (binding.editNameCategory.text.isNotEmpty()) {
                viewModelExpenses.updateCategoryName(
                    oldCategoryName,
                    binding.editNameCategory.text.toString()
                )
                dismiss()
            } else createAlertEmptyCategoryNameDialog()
        }
        return binding.root
    }

    private fun createAlertEmptyCategoryNameDialog() {
        val dialog = AlertEmptyCategoryDialog()
        activity?.supportFragmentManager?.let { it1 -> dialog.show(it1, "alertEmptyCategoryDialog2") }
    }
}