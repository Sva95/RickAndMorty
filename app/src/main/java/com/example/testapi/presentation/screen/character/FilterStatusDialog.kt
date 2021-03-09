package com.example.testapi.presentation.screen.character


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.navGraphViewModels
import com.example.testapi.R
import com.example.testapi.util.CharacterFilter
import kotlinx.android.synthetic.main.dialog_filter_status.*
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class FilterStatusDialog : DialogFragment() {

    private val characterViewModel: CharacterViewModel by sharedViewModel()
    private var positionGroup = 0

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
        val characterFilter = CharacterFilter()
        characterFilter.filterStatusPosition = positionGroup
        characterFilter.status = arrFilterSpecies[positionGroup]
        characterViewModel.setFilterStatus(characterFilter)
    }
}