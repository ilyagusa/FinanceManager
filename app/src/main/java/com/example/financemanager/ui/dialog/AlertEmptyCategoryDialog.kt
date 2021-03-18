package com.example.financemanager.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.financemanager.R
import com.example.financemanager.databinding.AlertEmptyCategoryBinding



class AlertEmptyCategoryDialog() : DialogFragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: AlertEmptyCategoryBinding = DataBindingUtil.inflate(
            inflater, R.layout.alert_empty_category, container, false
        )

        binding.buttonOk2.setOnClickListener() {
            dismiss()
        }

        return binding.root
    }
}