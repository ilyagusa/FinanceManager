package com.example.financemanager.ui.statistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.financemanager.R
import com.example.financemanager.databinding.FragmentStatisticsBinding

class StatisticsFragment : Fragment() {

    private lateinit var viewModel : StatisticsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: FragmentStatisticsBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_statistics, container, false)

        viewModel = ViewModelProvider(this).get(StatisticsViewModel::class.java)

        binding.textNotifications.text = viewModel.text.value
        return binding.root
    }
}