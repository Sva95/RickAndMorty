package com.example.testapi.presentation.adapter.viewholder

import android.view.View
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.example.testapi.base.BaseViewHolder
import com.example.testapi.databinding.ItemErrorPagingBinding
import com.example.testapi.presentation.entity.CharacterUiEntity


class ErrorPagingViewHolder(
    private val view: ItemErrorPagingBinding,
    private val retryCallback: (() -> Unit)?
) : RecyclerView.ViewHolder(view.root) {

    fun bind(loadState: LoadState) {
        with(view) {
            progressBar.isVisible = loadState is LoadState.Loading
            retryButton.isVisible = loadState is LoadState.Error
            errorMsg.isVisible = !(loadState as? LoadState.Error)?.error?.message.isNullOrBlank()
            errorMsg.text = (loadState as? LoadState.Error)?.error?.message

            retryButton.setOnClickListener {
                retryCallback?.invoke()
            }
        }
    }
}
