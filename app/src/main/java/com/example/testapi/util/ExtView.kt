package com.example.testapi.util
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions


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


fun ImageView.loadProfileImage(url: String){
    Glide.with(this)
        .load(url)
        .centerCrop()
        .skipMemoryCache(true)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}
