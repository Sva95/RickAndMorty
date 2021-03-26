package com.example.testapi.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.testapi.presentation.entity.CharacterUiEntity


abstract class BaseViewHolder<T>(itemView: ViewBinding) : RecyclerView.ViewHolder(itemView.root) {
    abstract fun bind(item: CharacterUiEntity, callback: (() -> Unit)? = null)
}
