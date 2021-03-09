package com.example.testapi.presentation.screen.character

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.DialogFragment
import androidx.navigation.navGraphViewModels
import com.example.testapi.R
import com.example.testapi.util.CharacterFilter
import kotlinx.android.synthetic.main.dialog_filter_species.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class FilterSpeciesDialog : DialogFragment() {

    private val characterViewModel: CharacterViewModel by sharedViewModel()
    private var positionGroup = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_filter_species, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        characterViewModel.observeFilterState.observe(viewLifecycleOwner, { characterFilter ->
            (radioGroup.getChildAt(characterFilter.filterSpeciesPosition) as RadioButton).isChecked =
                true
        })

        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val radioChecked: View = group.findViewById(checkedId)
            positionGroup = group.indexOfChild(radioChecked)
        }

        btn_ok.setOnClickListener {
            setFilterSpecies()
            dismiss()
        }
        btn_cancel.setOnClickListener { dismiss() }
    }

    private fun setFilterSpecies() {
        val arrFilterSpecies = resources.getStringArray(R.array.filterSpecies)
        if (positionGroup > arrFilterSpecies.size - 1) {
            return
        }
        val characterFilter = CharacterFilter()
        characterFilter.filterSpeciesPosition = positionGroup
        characterFilter.species = arrFilterSpecies[positionGroup]
        characterViewModel.setFilterSpecies(characterFilter)
    }

}