package com.example.testapi.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

import com.example.testapi.R
import com.example.testapi.base.BaseViewHolder
import com.example.testapi.presentation.adapter.viewholder.CharacterViewHolder
import com.example.testapi.presentation.adapter.viewholder.ErrorPagingViewHolder

import com.example.testapi.presentation.entity.CharacterEntity
import com.example.testapi.util.Constant.Companion.ERROR_TYPE_VIEW_HOLDER


class CharacterAdapter(
    var onRetry: (() -> Unit)? = null
) : RecyclerView.Adapter<BaseViewHolder<CharacterEntity>>() {

    private val characterList = mutableListOf<CharacterEntity>()

    fun setData(list: List<CharacterEntity>) {
        val diffCallback = CharacterDiffCallback(characterList, list)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.characterList.clear()
        this.characterList.addAll(list)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<CharacterEntity> {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return when (viewType) {
            R.layout.item_character -> CharacterViewHolder(view)
            R.layout.item_error_paging -> ErrorPagingViewHolder(view)
            else -> throw IllegalArgumentException("Unknown view type $viewType")
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (characterList[position].id != ERROR_TYPE_VIEW_HOLDER)
            return R.layout.item_character

        return R.layout.item_error_paging
    }

    override fun getItemCount(): Int {
        return characterList.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder<CharacterEntity>, position: Int) {
        when (getItemViewType(position)) {

            R.layout.item_error_paging -> if (holder is ErrorPagingViewHolder) {
                holder.bind(characterList[position], onRetry)
            }
            R.layout.item_character -> if (holder is CharacterViewHolder) {
                holder.bind(characterList[position])
            }
        }
    }

    fun removeErrorHolder() {
        characterList.removeAt(characterList.size - 1)
        notifyItemChanged(characterList.size - 1)
    }

    fun addPagingErrorItem(character: CharacterEntity) {
        characterList.add(character)
        notifyItemChanged(characterList.size - 1)
    }
}




