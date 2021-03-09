package com.example.testapi.presentation.screen.character

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.testapi.R
import com.example.testapi.presentation.adapter.CharacterAdapter
import com.example.testapi.presentation.adapter.WrapContentLinearLayoutManager
import com.example.testapi.presentation.entity.CharacterEntity
import com.example.testapi.util.*
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_character.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit


class CharacterFragment : Fragment(R.layout.activity_character) {

    private val viewModel: CharacterViewModel by viewModel()
    private var linearLayoutManager : WrapContentLinearLayoutManager? = WrapContentLinearLayoutManager(context)
    private lateinit var characterAdapter: CharacterAdapter
    private var isLoading = false

    private val debouncer = Debouncer(400, TimeUnit.MILLISECONDS) {
        viewModel.setFilterName(it)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        characterAdapter = CharacterAdapter()
        characterAdapter.onRetry = {
            viewModel.onLoadMore()
            characterAdapter.removeErrorHolder()
        }

        return inflater.inflate(R.layout.activity_character, container, false);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView()
        configureObservables()
    }

    private fun bindView() {

        with(rv_characters) {
            if (linearLayoutManager != null) {
                linearLayoutManager = null
                linearLayoutManager = WrapContentLinearLayoutManager(context)
                setLayoutManager(linearLayoutManager)
                addOnScrollListener(paginationScrollListener)
            }


            adapter = characterAdapter
            itemAnimator = null
        }

        sv_character.onQueryChange {
            debouncer.process(it)
        }

        sv_character.clearFocus()

        btn_retry_request.setOnClickListener {
            viewModel.onRetry()
        }

        img_sort_character_species.setOnClickListener {
            showFilterSpeciesDialog()
        }

        img_sort_character_status.setOnClickListener {
            showFilterStatusDialog()
        }
    }

    private fun configureObservables() {
/*        viewModel.character.observe(viewLifecycleOwner, {
            isLoading = false
            characterAdapter.setData(it)
        })*/

        viewModel.networkState.observe(viewLifecycleOwner, {
            println("OOOOOOBSERVE")
            when (it) {
                is CharacterState.NetworkError -> {
                    layout_error.visibility = View.VISIBLE
                    pb_load_content.visibility = View.GONE
                    rv_characters.visibility = View.INVISIBLE
                }
                is CharacterState.Success -> {
                    // rv_characters.addOnScrollListener(paginationScrollListener)
                    pb_load_content.visibility = View.GONE
                    layout_error.visibility = View.GONE
                    rv_characters.visibility = View.VISIBLE
                }
                is CharacterState.NetworkPagingError -> {
                    pb_load_content.visibility = View.GONE
                    layout_error.visibility = View.GONE
                    //  rv_characters.removeOnScrollListener(paginationScrollListener)
                    characterAdapter.addPagingErrorItem(CharacterEntity(id = Constant.ERROR_TYPE_VIEW_HOLDER))
                    rv_characters.scrollToPosition(characterAdapter.getItemCount() - 1);
                }
                is CharacterState.NotFound -> {
                    pb_load_content.visibility = View.GONE
                }
                is CharacterState.NotFoundMore -> {
                    Snackbar.make(
                        coordinatorLayout,
                        getString(R.string.no_more_items),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
                is CharacterState.Progress -> {
                    rv_characters.visibility = View.GONE
                    layout_error.visibility = View.GONE
                    pb_load_content.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun showFilterStatusDialog() {
        findNavController().navigate(R.id.filterStatusDialog)
    }

    private fun showFilterSpeciesDialog() {
        findNavController().navigate(R.id.filterSpeciesDialog)
    }

    private val paginationScrollListener =
        object : PaginationScrollListener(linearLayoutManager!!) {
            override fun loadMoreItems() {
                if (!isLoading) {
                    isLoading = true
                    viewModel.onLoadMore()
                }
            }
        }

}



