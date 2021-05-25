package com.example.testapi.presentation.screen.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.testapi.R
import com.example.testapi.presentation.adapter.CharacterAdapter
import com.example.testapi.presentation.adapter.WrapContentLinearLayoutManager
import com.example.testapi.presentation.entity.CharacterEntity
import com.example.testapi.util.*
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_character.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class CharacterActivity : AppCompatActivity(R.layout.activity_character) {

    private val viewModel: CharacterViewModel by viewModel()
    private val linearLayoutManager = WrapContentLinearLayoutManager(this)
    private lateinit var characterAdapter: CharacterAdapter
    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindView()
        configureObservables()
    }

    private fun bindView() {
        characterAdapter = CharacterAdapter()
        characterAdapter.adapterObserve {
            rv_characters.scrollToPosition(0);
            rv_characters.scheduleLayoutAnimation()
        }
        characterAdapter.onRetry = {
            viewModel.onRetryPaging()
            characterAdapter.removeErrorHolder(it)
        }

        rv_characters.apply {
            setLayoutManager(linearLayoutManager)
            addOnScrollListener(paginationScrollListener)
            adapter = characterAdapter
            itemAnimator = null
        }

        sv_character.onQueryChange {
            viewModel.updateCharacterName(it)
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
        viewModel.character.observe(this, {
            isLoading = false
            characterAdapter.setData(it)
        })

        viewModel.networkState.observe(this, {
            when (it) {
                is CharacterState.NetworkError -> {
                    layout_error.visibility = View.VISIBLE
                    pb_load_content.visibility = View.GONE
                    rv_characters.visibility = View.INVISIBLE
                }
                is CharacterState.Success -> {
                    rv_characters.addOnScrollListener(paginationScrollListener)
                    pb_load_content.visibility = View.GONE
                    layout_error.visibility = View.GONE
                    rv_characters.visibility = View.VISIBLE
                }
                is CharacterState.NetworkPagingError -> {
                    pb_load_content.visibility = View.GONE
                    layout_error.visibility = View.GONE
                    rv_characters.removeOnScrollListener(paginationScrollListener)
                    characterAdapter.addPagingErrorItem(CharacterEntity(id = Constant.ERROR_TYPE_VIEW_HOLDER))
                    rv_characters.scrollToPosition(characterAdapter.getItemCount() - 1)
                    rv_characters.visibility = View.VISIBLE
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
                    layout_error.visibility = View.GONE
                    pb_load_content.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun showFilterStatusDialog() {
        val dialog = FilterStatusDialog.newInstance()
        dialog.show(supportFragmentManager, null)
    }

    private fun showFilterSpeciesDialog() {
        val dialog = FilterSpeciesDialog.newInstance()
        dialog.show(supportFragmentManager, null)
    }

    private val paginationScrollListener = object : PaginationScrollListener(linearLayoutManager) {
        override fun loadMoreItems() {
            if (!isLoading) {
                isLoading = true
                viewModel.onLoadMore()
            }
        }
    }
}



