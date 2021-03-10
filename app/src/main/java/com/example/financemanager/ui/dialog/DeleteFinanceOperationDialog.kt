package com.example.financemanager.ui.dialog

import com.example.financemanager.database.financeOperationDatabase.FinanceOperation
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.financemanager.R
import com.example.financemanager.database.financeOperationDatabase.FinanceOperationDatabase
import com.example.financemanager.databinding.FragmentDeleteFinanceOperationBinding
import com.example.financemanager.ui.history.HistoryViewModel
import com.example.financemanager.ui.history.HistoryViewModelFactory


class DeleteFinanceOperationDialog(item: FinanceOperation) : DialogFragment() {

    private lateinit var viewModelHistory: HistoryViewModel
    private var item: FinanceOperation = item

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentDeleteFinanceOperationBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_delete_finance_operation, container, false)
        val application = requireNotNull(this.activity).application
        val daoFinanceOperationDao = FinanceOperationDatabase.getInstance(application).getFinanceOperationDatabaseDao()
        val viewModelFactoryHistory = HistoryViewModelFactory(daoFinanceOperationDao, application)
        viewModelHistory =  ViewModelProvider(this,viewModelFactoryHistory).get(HistoryViewModel::class.java)

        binding.buttonSubmitDeleteFinanceOperation.setOnClickListener() {
            viewModelHistory.deleteFinanceOperation(item)
            dismiss()
        }

        binding.buttonCancelDeleteFinanceOperation.setOnClickListener() {
            dismiss()
        }
        return binding.root
    }
}