package com.example.testapi.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.testapi.R
import com.example.testapi.presentation.adapter.viewholder.ErrorPagingViewHolder
import kotlinx.android.synthetic.main.item_error_paging.view.*

class PagingLoadStateAdapter<T : Any, VH : RecyclerView.ViewHolder>(
    private val adapter: PagingDataAdapter<T, VH>
) : LoadStateAdapter<ErrorPagingViewHolder>() {

    override fun onBindViewHolder(holder: ErrorPagingViewHolder, loadState: LoadState) =
        holder.bind(loadState)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): ErrorPagingViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_error_paging, parent, false)
        return ErrorPagingViewHolder(view, { adapter.refresh() })
    }
}