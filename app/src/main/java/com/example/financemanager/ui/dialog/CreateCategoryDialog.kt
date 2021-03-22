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
import com.example.financemanager.databinding.FragmentCreateNewCategoryBinding
import com.example.financemanager.ui.home.HomeViewModel
import com.example.financemanager.ui.home.HomeViewModelFactory
import kotlinx.android.synthetic.main.alert_empty_category.*


class CreateCategoryDialog() : DialogFragment() {

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentCreateNewCategoryBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_create_new_category, container, false
        )
        val application = requireNotNull(this.activity).application
        val expensesCategoryDao = ExpensesCategoryDatabase.getInstance(application).getExpensesCategoryDatabaseDao()
        val viewModelFactory = HomeViewModelFactory(expensesCategoryDao, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)


        binding.buttonCreateNewCategory.setOnClickListener() {
            val categoryName = binding.editCreateNewCategory.text.toString()
            if (categoryName.isNotEmpty()) {
                Toast.makeText(this.context,"Категория !", Toast.LENGTH_SHORT).show()
                viewModel.insertButton(categoryName)
                dismiss()
            } else createAlertEmptyCategoryNameDialog()
        }

        binding.buttonCancelNewCategory.setOnClickListener() {
            dismiss()
        }

        return binding.root
    }

    private fun createAlertEmptyCategoryNameDialog() {
        val dialog = AlertEmptyCategoryDialog()
        activity?.supportFragmentManager?.let { it1 -> dialog.show(it1, "alertEmptyCategoryDialog") }
    }
}