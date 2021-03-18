package com.example.testapi.presentation.adapter.viewholder

import android.view.View
import com.example.testapi.base.BaseViewHolder
import com.example.testapi.presentation.entity.CharacterEntity
import kotlinx.android.synthetic.main.item_error_paging.view.*

class ErrorPagingViewHolder(private val view: View) : BaseViewHolder<CharacterEntity>(view) {

    override fun bind(item: CharacterEntity, callback: ((Int) -> Unit)?) {
        view.btn_retry.setOnClickListener {
            callback?.invoke(adapterPosition)
        }
    }
}
