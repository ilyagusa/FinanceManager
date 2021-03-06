package com.example.financemanager.ui.home

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.example.financemanager.R
import com.example.financemanager.database.expensesCategoryDatabase.ExpensesCategoryDatabase
import com.example.financemanager.database.financeOperationDatabase.FinanceOperationDatabase
import com.example.financemanager.databinding.FragmentHomeBinding
import com.example.financemanager.ui.home.expensesCategory.ExpensesCategoryAdapter
import com.example.financemanager.ui.home.expensesCategory.ExpensesCategoryViewModel
import com.example.financemanager.ui.home.expensesCategory.ExpensesCategoryViewModelFactory


class HomeFragment : Fragment() {

    private lateinit var viewModel : HomeViewModel
    private lateinit var viewModelExpenses : ExpensesCategoryViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: FragmentHomeBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_home, container, false)

        val application = requireNotNull(this.activity).application
        val expensesCategoryDao = ExpensesCategoryDatabase.getInstance(application).getExpensesCategoryDatabaseDao()
        val daoFinanceOperationDao = FinanceOperationDatabase.getInstance(application).getFinanceOperationDatabaseDao()
        val viewModelFactory = HomeViewModelFactory(expensesCategoryDao, application)
        val viewModelFactoryExpensesCategory = ExpensesCategoryViewModelFactory(expensesCategoryDao, daoFinanceOperationDao, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
        viewModelExpenses = ViewModelProvider(this, viewModelFactoryExpensesCategory).get(ExpensesCategoryViewModel::class.java)

        val adapter = ExpensesCategoryAdapter(viewModelExpenses, this.context as Activity)
        binding.buttonListExpenses.adapter = adapter

        binding.addCategoryButton.setOnClickListener(){
            viewModel.insertButton("Новая категория из кнопки Новая категория из кнопки Новая категория из кнопки Новая категория из кнопки Новая категория из кнопки")
        }

        binding.buttonSalary.setOnClickListener(){
            viewModel.onClear()
        }

        viewModel.expensesList.observe(viewLifecycleOwner, Observer { expensesList ->
            if (expensesList != null)
                adapter.data = expensesList
        })

        createPieChart(binding)
        return binding.root
    }

    private fun createPieChart(binding : FragmentHomeBinding){
        val pie = AnyChart.pie()
        val data: ArrayList<DataEntry> = ArrayList()
        data.add(ValueDataEntry("Доходы", 50000))
        data.add(ValueDataEntry("Расходы", 18000))
        pie.data(data)
        pie.title("Февраль 2021")
        val anyChartView = binding.pieChartView
        anyChartView.setChart(pie)

    }
}