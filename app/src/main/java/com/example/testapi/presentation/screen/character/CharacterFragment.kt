package com.example.testapi.presentation.screen.character

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.testapi.R
import com.example.testapi.base.BaseFragment
import com.example.testapi.databinding.FragmentCharacterBinding
import com.example.testapi.presentation.adapter.PagingLoadStateAdapter
import com.example.testapi.paging.CharactersAdapter
import com.example.testapi.util.*
import kotlinx.coroutines.flow.collectLatest
import org.koin.android.ext.android.getKoin
import org.koin.androidx.viewmodel.ViewModelParameter
import org.koin.androidx.viewmodel.compat.ScopeCompat.viewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.androidx.viewmodel.koin.getViewModel
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import org.koin.core.scope.Scope
import org.koin.java.KoinJavaComponent.getKoin


class CharacterFragment :
    BaseFragment<FragmentCharacterBinding>(FragmentCharacterBinding::inflate) {

    private val viewModel: CharacterViewModel by viewModel()
    private lateinit var characterAdapter: CharactersAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView()

        lifecycleScope.launchWhenCreated {
            viewModel.characters.collectLatest {
                characterAdapter.submitData(it)
            }
        }
    }

    private fun bindView() {
        characterAdapter = CharactersAdapter()
        with(characterAdapter) {
            binding.rvCharacters.adapter = withLoadStateHeaderAndFooter(
                header = PagingLoadStateAdapter(this),
                footer = PagingLoadStateAdapter(this)
            )
        }

        with(binding) {
            svCharacter.onQueryChange {
                viewModel.setFilterName(it)
            }

            svCharacter.clearFocus()
            imgSortCharacterSpecies.setOnClickListener {
                showFilterSpeciesDialog()
            }

            imgSortCharacterStatus.setOnClickListener {
                showFilterStatusDialog()
            }

        }

    }

    private fun showFilterStatusDialog() {
        findNavController().navigate(R.id.filterStatusDialog)
    }

    private fun showFilterSpeciesDialog() {
        findNavController().navigate(R.id.filterSpeciesDialog)
    }
}


