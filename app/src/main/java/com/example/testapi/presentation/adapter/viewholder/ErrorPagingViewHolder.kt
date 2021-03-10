package com.example.testapi.presentation.adapter.viewholder

import android.view.View
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.example.testapi.base.BaseViewHolder
import com.example.testapi.presentation.entity.CharacterUiEntity
import kotlinx.android.synthetic.main.item_error_paging.view.*

class ErrorPagingViewHolder(
    private val view: View,
    private val retryCallback: (() -> Unit)?
) : RecyclerView.ViewHolder(view) {

    fun bind(loadState: LoadState) {
        with(view) {
            progress_bar.isVisible = loadState is LoadState.Loading
            retry_button.isVisible = loadState is LoadState.Error
            error_msg.isVisible = !(loadState as? LoadState.Error)?.error?.message.isNullOrBlank()
            error_msg.text = (loadState as? LoadState.Error)?.error?.message

            retry_button.setOnClickListener {
                retryCallback?.invoke()
            }
        }
    }
}
