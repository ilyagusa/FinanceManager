package com.example.financemanager.ui.statistics

import android.os.Bundle;
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.MarkerType;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;
import com.example.financemanager.R
import com.example.financemanager.database.expensesCategoryDatabase.ExpensesCategoryDatabase
import com.example.financemanager.database.financeOperationDatabase.FinanceOperationDatabase
import com.example.financemanager.databinding.FragmentStatisticsBinding
import com.example.financemanager.ui.home.expensesCategory.FinanceOperationViewModel
import com.example.financemanager.ui.home.expensesCategory.FinanceOperationViewModelFactory

class StatisticsFragment : Fragment() {

    private lateinit var viewModelStatistics: StatisticsViewModel
    private var dataLinearChart: MutableList<DataEntry> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentStatisticsBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_statistics, container, false
        )
        val application = requireNotNull(this.activity).application

        val daoFinanceOperationDao =
            FinanceOperationDatabase.getInstance(application).getFinanceOperationDatabaseDao()
        val viewModelFactoryStatistics = StatisticsViewModelFactory(
            daoFinanceOperationDao,
            application
        )
        viewModelStatistics = ViewModelProvider(
            this,
            viewModelFactoryStatistics
        ).get(StatisticsViewModel::class.java)


        //Создание Линенйной графика, содержащего информацию о балансе
        var cartesian = AnyChart.line()
        dataLinearChart.add(ValueDataEntry("1", 0))

        viewModelStatistics.listBalanceOfMonth.observe(viewLifecycleOwner, Observer { listBalance ->
            cartesian.removeAllSeries()
            for (dayBalance in listBalance) {
                dataLinearChart.add(dayBalance)
            }
            createCartesian(cartesian, dataLinearChart)
        })

        createLinearGraphic(binding, cartesian)

        return binding.root
    }


    private fun createCartesian(
        cartesian: Cartesian,
        seriesDataValue: MutableList<DataEntry>
    ) {
        cartesian.animation(true)
        cartesian.padding(10.0, 20.0, 5.0, 20.0)
        cartesian.crosshair().enabled(true)
        cartesian.crosshair()
            .yLabel(true)
            .yStroke(null as Stroke?, null, null, null as String?, null as String?)
        cartesian.tooltip().positionMode(TooltipPositionMode.POINT)
        cartesian.title(resources.getString(R.string.text_title_linear_chart))
        cartesian.yAxis(0).title(resources.getString(R.string.balance))
        cartesian.xAxis(0).labels().padding(5.0, 5.0, 5.0, 5.0)

        val set: Set = Set.instantiate()
        set.data(seriesDataValue) 
        val series1Mapping: Mapping = set.mapAs("{ x: 'x', value: 'value' }")
        val series1: Line = cartesian.line(series1Mapping)
        series1.name(resources.getString(R.string.balance))
        series1.hovered().markers().enabled(true)
        series1.hovered().markers()
            .type(MarkerType.CIRCLE)
            .size(4.0)
        series1.tooltip()
            .position("right")
            .anchor(Anchor.LEFT_CENTER)
            .offsetX(5.0)
            .offsetY(5.0)
        cartesian.legend().enabled(true)
        cartesian.legend().fontSize(13.0)
        cartesian.legend().padding(0.0, 0.0, 10.0, 0.0)
    }

    private fun createLinearGraphic(binding: FragmentStatisticsBinding, cartesian: Cartesian) {
        val anyChartView: AnyChartView = binding.anyChartView
        anyChartView.setProgressBar(binding.progressBar)
        anyChartView.setChart(cartesian)
    }

}



