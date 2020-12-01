package com.example.testapi.util
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


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
