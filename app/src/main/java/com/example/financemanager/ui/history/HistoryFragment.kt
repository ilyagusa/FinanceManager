package com.example.financemanager.ui.history

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.financemanager.R
import com.example.financemanager.database.financeOperationDatabase.FinanceOperationDatabase
import com.example.financemanager.databinding.FragmentHistoryBinding

class HistoryFragment : Fragment() {

    private lateinit var viewModel : HistoryViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: FragmentHistoryBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_history, container, false)

        val application = requireNotNull(this.activity).application
        val daoFinanceOperationDao = FinanceOperationDatabase.getInstance(application).getFinanceOperationDatabaseDao()
        val viewModelFactoryHistory = HistoryViewModelFactory(daoFinanceOperationDao, application)
        viewModel = ViewModelProvider(this, viewModelFactoryHistory).get(HistoryViewModel::class.java)

        val adapter = FinanceOperationAdapter(this.context as Activity)
        binding.recyclerViewHistory.adapter = adapter

        viewModel.operationList.observe(viewLifecycleOwner, Observer { operationList ->
            if (operationList != null)
                adapter.data = operationList
        })


        return binding.root
    }
}