package com.example.testapi.presentation.screen.main


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.example.testapi.R
import com.example.testapi.util.CharacterFilter
import kotlinx.android.synthetic.main.dialog_filter_status.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class FilterStatusDialog : DialogFragment() {

    private val characterViewModel: CharacterViewModel by sharedViewModel()
    private var positionGroup = 0

    companion object {
        fun newInstance(): FilterStatusDialog {
            return FilterStatusDialog()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_filter_status, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        characterViewModel.observeFilterState.observe(viewLifecycleOwner) { characterFilter ->
            (radioGroup.getChildAt(characterFilter.filterStatusPosition) as RadioButton).isChecked =
                true
        }
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val radioChecked: View = group.findViewById(checkedId)
            positionGroup = group.indexOfChild(radioChecked)
        }
        btn_ok.setOnClickListener {
            setFilterStatus()
            dismiss()
        }
        btn_cancel.setOnClickListener { dismiss() }
    }

    private fun setFilterStatus() {
        val arrFilterSpecies = resources.getStringArray(R.array.filterStatus)
        if (positionGroup > arrFilterSpecies.size - 1) {
            return
        }
        CharacterFilter().apply {
            filterStatusPosition = positionGroup
            status = arrFilterSpecies[positionGroup]
        }.also { characterViewModel.updateFilterStatus(it) }

    }
}