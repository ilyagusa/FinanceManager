package com.example.financemanager.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.financemanager.R
import com.example.financemanager.databinding.FragmentInfoMessageBinding


class InfoMessageDialog() : DialogFragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentInfoMessageBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_info_message, container, false
        )

        var textInfoMessage = "Пустое сообщения"
        val textInfoMessageArg = arguments?.getString("info_message")

        if (textInfoMessageArg != null) {
            textInfoMessage = textInfoMessageArg
        }

        binding.textInfoMessage.text = textInfoMessage

            return binding.root
    }
}