package com.example.financemanager.ui.home

import android.app.Activity
import android.app.Dialog
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
import com.anychart.charts.Pie
import com.example.financemanager.R
import com.example.financemanager.database.expensesCategoryDatabase.ExpensesCategoryDatabase
import com.example.financemanager.database.financeOperationDatabase.FinanceOperationDatabase
import com.example.financemanager.databinding.FragmentHomeBinding
import com.example.financemanager.ui.dialog.CreateCategoryDialog
import com.example.financemanager.ui.dialog.CreateIncomeDialog
import com.example.financemanager.ui.home.expensesCategory.ExpensesCategoryAdapter
import com.example.financemanager.ui.home.expensesCategory.FinanceOperationViewModel
import com.example.financemanager.ui.home.expensesCategory.FinanceOperationViewModelFactory
import kotlinx.android.synthetic.main.alert_empty_category.*
import kotlinx.android.synthetic.main.fragment_create_new_category.*


class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var viewModelExpenses: FinanceOperationViewModel
    private val dataPieChart : ArrayList<DataEntry> = arrayListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: FragmentHomeBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_home, container, false)
        val application = requireNotNull(this.activity).application
        val expensesCategoryDao = ExpensesCategoryDatabase.getInstance(application).getExpensesCategoryDatabaseDao()
        val daoFinanceOperationDao = FinanceOperationDatabase.getInstance(application).getFinanceOperationDatabaseDao()
        val viewModelFactory = HomeViewModelFactory(expensesCategoryDao, application)
        val viewModelFactoryExpensesCategory = FinanceOperationViewModelFactory(expensesCategoryDao, daoFinanceOperationDao, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
        viewModelExpenses = ViewModelProvider(this, viewModelFactoryExpensesCategory).get(FinanceOperationViewModel::class.java)
        val adapter = ExpensesCategoryAdapter(viewModelExpenses, this.context as Activity)
        binding.buttonListExpenses.adapter = adapter

        var pie = AnyChart.pie()
        dataPieChart.add(ValueDataEntry(resources.getString(R.string.income), 0))
        dataPieChart.add(ValueDataEntry(resources.getString(R.string.expenses), 0))

        binding.addCategoryButton.setOnClickListener() {
            //createNewCategoryDialog()
            val dialog = CreateCategoryDialog(viewModel)
            activity?.supportFragmentManager?.let { it1 -> dialog.show(it1, "createCategoryDialog") }
        }

        binding.buttonSalary.setOnClickListener() {
            val dialog = CreateIncomeDialog(resources.getString(R.string.salary))
            activity?.supportFragmentManager?.let { it1 -> dialog.show(it1, "createIncomeDialog") }
        }

        binding.buttonPartTimeJob.setOnClickListener() {
            val dialog = CreateIncomeDialog(resources.getString(R.string.part_time_job))
            activity?.supportFragmentManager?.let { it1 -> dialog.show(it1, "createIncomeDialog1") }
        }

        binding.buttonOtherIncome.setOnClickListener() {
            val dialog = CreateIncomeDialog(resources.getString(R.string.other_income))
            activity?.supportFragmentManager?.let { it1 -> dialog.show(it1, "createIncomeDialog2") }
        }

        viewModelExpenses.sumExpensesByMonth?.observe(viewLifecycleOwner, Observer { sumExpensesByMonth ->
            if(sumExpensesByMonth != null) {
                binding.textExp.setText(sumExpensesByMonth.toString())
                dataPieChart[1] = ValueDataEntry(resources.getString(R.string.expenses), sumExpensesByMonth*(-1))
            }
            else {
                binding.textExp.setText("0.00")
                dataPieChart[1] = ValueDataEntry(resources.getString(R.string.expenses), 0)
            }
            pie.data(dataPieChart)
        })

        viewModelExpenses.sumIncomeByMonth?.observe(viewLifecycleOwner, Observer { sumIncomeByMonth ->
            if(sumIncomeByMonth != null) {
                binding.textInc.setText(sumIncomeByMonth.toString())
                dataPieChart[0] = ValueDataEntry(resources.getString(R.string.income), 0.0 + sumIncomeByMonth.toDouble())
            }
            else {
                binding.textInc.setText("0.00")
                dataPieChart[0] = ValueDataEntry(resources.getString(R.string.income), 0.0)
            }
            pie.data(dataPieChart)
        })

        viewModelExpenses.sumAllOperation?.observe(viewLifecycleOwner, Observer { sumAllOperation ->
            if(sumAllOperation != null) {
                binding.textAll.setText(sumAllOperation.toString())
            }
            else binding.textAll.setText("0.00")
        })


        viewModel.expensesList.observe(viewLifecycleOwner, Observer { expensesList ->
            if (expensesList != null)
                adapter.data = expensesList
        })

        createPieChart(binding, pie)

        return binding.root
    }

    private fun createNewCategoryDialog() {
        val dialog: Dialog = Dialog(this.context as Activity)
        dialog.setContentView(R.layout.fragment_create_new_category)

        dialog.button_create_new_category.setOnClickListener() {
            if (dialog.edit_create_new_category.text.isNotEmpty()) {
                val nameCategory: String = dialog.edit_create_new_category.text.toString()
                viewModel.checkContainCategoryInDatabase(nameCategory)
                dialog.dismiss()
            } else {
                createAlertEmptyCategoryNameDialog()
            }
        }
        dialog.button_cancel_new_category.setOnClickListener() {
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun createAlertEmptyCategoryNameDialog() {
        val alertDialogEmptyAmount: Dialog = Dialog(this.context as Activity)
        alertDialogEmptyAmount.setContentView(R.layout.alert_empty_category)
        alertDialogEmptyAmount.button_ok2.setOnClickListener() {
            alertDialogEmptyAmount.dismiss()
        }
        alertDialogEmptyAmount.show()
    }

    private fun createPieChart(binding: FragmentHomeBinding, pie: Pie) {
        pie.title(viewModel.dateString)
        val anyChartView = binding.pieChartView
        anyChartView.setChart(pie)
    }
}