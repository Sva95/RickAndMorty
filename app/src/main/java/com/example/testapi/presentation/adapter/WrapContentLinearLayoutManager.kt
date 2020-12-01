package com.example.testapi.presentation.adapter

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

 class WrapContentLinearLayoutManager(context: Context) : LinearLayoutManager(context) {
     private var isScrollEnabled = false

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        try {
            super.onLayoutChildren(recycler, state);
            setAutoMeasureEnabled(false);
        } catch (e: IndexOutOfBoundsException) {
            Log.e("Error", "IndexOutOfBoundsException in RecyclerView happens");
        }
    }


     fun setScrollEnabled(flag: Boolean) {
         this.isScrollEnabled = flag
     }

     override fun canScrollVertically(): Boolean {
         return !isScrollEnabled && super.canScrollVertically()
     }
}


