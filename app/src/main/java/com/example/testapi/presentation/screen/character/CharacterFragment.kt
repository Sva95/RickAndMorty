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
import com.example.testapi.presentation.adapter.PagingLoadStateAdapter
import com.example.testapi.paging.CharactersAdapter
import com.example.testapi.util.*
import kotlinx.android.synthetic.main.fragment_character.*
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


class CharacterFragment : Fragment() {

    private val viewModel: CharacterViewModel by viewModel()
    private lateinit var characterAdapter: CharactersAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_character, container, false);
    }

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
            rv_characters.adapter = withLoadStateHeaderAndFooter(
                header = PagingLoadStateAdapter(this),
                footer = PagingLoadStateAdapter(this)
            )
        }

        sv_character.onQueryChange {
            viewModel.setFilterName(it)
        }

        sv_character.clearFocus()

        img_sort_character_species.setOnClickListener {
            showFilterSpeciesDialog()
        }

        img_sort_character_status.setOnClickListener {
            showFilterStatusDialog()
        }
    }

    private fun showFilterStatusDialog() {
        findNavController().navigate(R.id.filterStatusDialog)
    }

    private fun showFilterSpeciesDialog() {
        findNavController().navigate(R.id.filterSpeciesDialog)
    }
}


