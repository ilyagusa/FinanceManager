package com.example.financemanager.ui.dialog

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.financemanager.R
import com.example.financemanager.database.expensesCategoryDatabase.ExpensesCategoryDatabase
import com.example.financemanager.database.financeOperationDatabase.FinanceOperationDatabase
import com.example.financemanager.databinding.FragmentCreateNewCategoryBinding
import com.example.financemanager.ui.home.HomeViewModel
import com.example.financemanager.ui.home.HomeViewModelFactory
import kotlinx.android.synthetic.main.alert_empty_category.*


class CreateCategoryDialog(viewModel: HomeViewModel) : DialogFragment() {

    private  var viewModel: HomeViewModel = viewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentCreateNewCategoryBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_create_new_category, container, false)
        binding.buttonCreateNewCategory.setOnClickListener() {
            if (binding.editCreateNewCategory.text.isNotEmpty()) {
                viewModel.checkContainCategoryInDatabase(binding.editCreateNewCategory.text.toString())
                dismiss()
            }
            else createAlertEmptyCategoryNameDialog()
        }

        binding.buttonCancelNewCategory.setOnClickListener() {
            dismiss()
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