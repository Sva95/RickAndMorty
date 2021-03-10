package com.example.testapi.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.testapi.R
import com.example.testapi.presentation.adapter.viewholder.CharacterViewHolder
import com.example.testapi.presentation.entity.CharacterUiEntity

class CharactersAdapter(
    var onClickProfile: (() -> Unit)? = null) :
    PagingDataAdapter<CharacterUiEntity, RecyclerView.ViewHolder>(CharacterComparator) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position)?.let {
            (holder as? CharacterViewHolder)?.bind(it, onClickProfile)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_character
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return CharacterViewHolder(view)
    }

}

object CharacterComparator : DiffUtil.ItemCallback<CharacterUiEntity>() {
    override fun areItemsTheSame(oldItem: CharacterUiEntity, newItem: CharacterUiEntity) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: CharacterUiEntity, newItem: CharacterUiEntity) =
        oldItem == newItem
}