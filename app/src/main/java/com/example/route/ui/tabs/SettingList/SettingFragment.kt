package com.example.route.ui.tabs.SettingList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.example.route.R
import com.example.route.databinding.FragmentSettingBinding


class SettingFragment : Fragment() {

    lateinit var viewBinding: FragmentSettingBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewBinding = FragmentSettingBinding.inflate(
            inflater,container,false
        )
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val modeSpinner = viewBinding.languageSpinner

        val modeAdapter = ArrayAdapter
            .createFromResource(
                requireContext(), R.array.language_array,
                android.R.layout.simple_spinner_item
            )

        modeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        modeSpinner.adapter = modeAdapter


        val languageSpinner = viewBinding.modeSpinner

        val languageAdapter=ArrayAdapter
                .createFromResource(
        requireContext(), R.array.mode_array,
        android.R.layout.simple_spinner_item
        )

      languageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        languageSpinner.adapter=languageAdapter




    }


}