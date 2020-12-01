package com.example.testapi.presentation.screen.main


import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer

import com.example.testapi.R
import com.example.testapi.util.CharacterFilter
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class FilterStatusDialog : DialogFragment() {

    private val viewModel: CharacterViewModel by sharedViewModel()
    private var characterFilter: CharacterFilter? = null

    companion object {
        fun newInstance(): FilterStatusDialog {
            return FilterStatusDialog()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // this line causes memory leak and if we use DialogFragment with onCreateDialog we have to use "this"
        // the best way to avoid this is to create an xml layout and override onCreateView and onViewCreated
        viewModel.observeFilterState.observe(requireActivity(), Observer {
            characterFilter = it
        })

        val filterOptions = getResources().getStringArray(R.array.filterStatus)
        var selectedItem = characterFilter?.filterStatusPosition ?: 0

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(R.string.select_filter_status)
        builder.setSingleChoiceItems(
            filterOptions,
            selectedItem,
            { _: DialogInterface, item: Int ->
                selectedItem = item
            })
        builder.setPositiveButton(R.string.ok, { dialogInterface: DialogInterface, _: Int ->
            val status =
                if (filterOptions[selectedItem].equals(filterOptions[0])) "" else filterOptions[selectedItem]
            viewModel.setFilterStatus(
                characterFilter = CharacterFilter(
                    status = status,
                    filterStatusPosition = selectedItem
                )
            )
            dialogInterface.dismiss()
        })
        builder.setNegativeButton(R.string.cancel, { dialogInterface: DialogInterface, _: Int ->
            dialogInterface.dismiss()
        })
        builder.create()
        return builder.show()
    }
}