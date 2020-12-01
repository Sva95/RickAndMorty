package com.example.testapi.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.testapi.presentation.entity.CharacterEntity


class CharacterDiffCallback(
    private val oldList: List<CharacterEntity>,
    private val newList: List<CharacterEntity>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].name == newList[newItemPosition].name
    }

}