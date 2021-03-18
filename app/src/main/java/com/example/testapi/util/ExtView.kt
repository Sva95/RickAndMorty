package com.example.testapi.util

import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testapi.base.BaseViewHolder


fun SearchView.onQueryChange(query: (String) -> Unit) {
    setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(text: String): Boolean {
            clearFocus()
            return true
        }

        override fun onQueryTextChange(newText: String): Boolean {
            query(newText)
            return true
        }
    })
}


fun <T> RecyclerView.Adapter<BaseViewHolder<T>>.adapterObserve(change: (() -> Unit)? = null) {
    registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            change?.invoke()
        }

        override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
            change?.invoke()
        }
    })
}
