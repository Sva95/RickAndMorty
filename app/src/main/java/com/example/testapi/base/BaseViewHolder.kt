package com.example.testapi.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.testapi.presentation.entity.CharacterUiEntity


abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(item: CharacterUiEntity, callback: (() -> Unit)? = null)
}
