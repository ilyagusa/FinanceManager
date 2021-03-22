package com.example.financemanager.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.financemanager.R
import com.example.financemanager.database.expensesCategoryDatabase.ExpensesCategoryDatabase
import com.example.financemanager.databinding.FragmentDeleteFinanceOperationBinding
import com.example.financemanager.ui.history.HistoryViewModel
import com.example.financemanager.ui.history.HistoryViewModelFactory


class DeleteFinanceOperationDialog() : DialogFragment() {

    private lateinit var viewModelHistory: HistoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentDeleteFinanceOperationBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_delete_finance_operation, container, false
        )
        val application = requireNotNull(this.activity).application
        val daoFinanceOperationDao =
            ExpensesCategoryDatabase.getInstance(application).getFinanceOperationDatabaseDao()
        val viewModelFactoryHistory = HistoryViewModelFactory(daoFinanceOperationDao, application)
        viewModelHistory =
            ViewModelProvider(this, viewModelFactoryHistory).get(HistoryViewModel::class.java)

        var id: Long = 123456789
        val idArg = arguments?.getLong("id")
        if (idArg != null) {
            id = idArg
        }

        binding.buttonSubmitDeleteFinanceOperation.setOnClickListener() {
            viewModelHistory.deleteFinanceOperationById(id)
            dismiss()
        }

        binding.buttonCancelDeleteFinanceOperation.setOnClickListener() {
            dismiss()
        }
        return binding.root
    }
}