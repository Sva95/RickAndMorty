package com.example.testapi.presentation.screen.character

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.testapi.R
import com.example.testapi.databinding.DialogFilterSpeciesBinding
import com.example.testapi.databinding.DialogFilterStatusBinding
import com.example.testapi.util.CharacterFilter
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class FilterSpeciesDialog : DialogFragment() {

    private var _binding: DialogFilterSpeciesBinding? = null
    private val binding get() = _binding!!

    private val characterViewModel: CharacterViewModel by sharedViewModel()
    private var positionGroup = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogFilterSpeciesBinding.inflate(LayoutInflater.from(context))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            characterViewModel.filterChannel.collect { characterFilter ->
                (binding.radioGroup.getChildAt(characterFilter.filterSpeciesPosition) as RadioButton).isChecked =
                    true
            }
        }
        
        with(binding) {
            radioGroup.setOnCheckedChangeListener { group, checkedId ->
                val radioChecked: View = group.findViewById(checkedId)
                positionGroup = group.indexOfChild(radioChecked)
            }

            btnOk.setOnClickListener {
                setFilterSpecies()
                dismiss()
            }

            btnCancel.setOnClickListener { dismiss() }

        }
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}