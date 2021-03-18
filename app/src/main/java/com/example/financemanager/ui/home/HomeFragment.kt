package com.example.financemanager.ui.home

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
import com.example.financemanager.ui.dialog.DeleteCategoryDialog
import com.example.financemanager.ui.home.expensesCategory.ExpensesCategoryAdapter
import com.example.financemanager.ui.home.expensesCategory.FinanceOperationViewModel
import com.example.financemanager.ui.home.expensesCategory.FinanceOperationViewModelFactory

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
        val adapter = ExpensesCategoryAdapter()
        binding.buttonListExpenses.adapter = adapter

        var pie = AnyChart.pie()
        dataPieChart.add(ValueDataEntry(resources.getString(R.string.income), 0))
        dataPieChart.add(ValueDataEntry(resources.getString(R.string.expenses), 0))

        binding.addCategoryButton.setOnClickListener() {
            val dialog = CreateCategoryDialog()
            activity?.supportFragmentManager?.let { it1 -> dialog.show(it1, "createCategoryDialog") }
        }

        binding.buttonSalary.setOnClickListener() {
            val args: Bundle = Bundle()
            args.putString("income_category_name", resources.getString(R.string.salary))
            val dialog = CreateIncomeDialog()
            dialog.arguments = args
            activity?.supportFragmentManager?.let { it1 -> dialog.show(it1, "createIncomeDialog1") }
        }

        binding.buttonPartTimeJob.setOnClickListener() {
            val args: Bundle = Bundle()
            args.putString("income_category_name", resources.getString(R.string.part_time_job))
            val dialog = CreateIncomeDialog()
            dialog.arguments = args
            activity?.supportFragmentManager?.let { it1 -> dialog.show(it1, "createIncomeDialog2") }
        }

        binding.buttonOtherIncome.setOnClickListener() {
            val args: Bundle = Bundle()
            args.putString("income_category_name", resources.getString(R.string.other_income))
            val dialog = CreateIncomeDialog()
            dialog.arguments = args
            activity?.supportFragmentManager?.let { it1 -> dialog.show(it1, "createIncomeDialog3") }
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


    private fun createPieChart(binding: FragmentHomeBinding, pie: Pie) {
        pie.title(viewModel.dateString)
        val anyChartView = binding.pieChartView
        anyChartView.setChart(pie)
    }
}