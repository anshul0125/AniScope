package com.example.aniscope.base

import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class PaginationListenerLinear(
    private val layoutManager: LinearLayoutManager,
    private val pageSize: Int
) : RecyclerView.OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val totalItemCount = layoutManager.itemCount
        val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
        Log.e("PaginationListenerGrid", "onScrolled, isLoading = $isLoading")
        Log.e("PaginationListenerGrid", "onScrolled, !isLoading = ${!isLoading}")
        Log.e("PaginationListenerGrid", "onScrolled, totalItemCount = $totalItemCount")
        Log.e("PaginationListenerGrid", "onScrolled, lastVisibleItemPosition = $lastVisibleItemPosition")
        Log.e("PaginationListenerGrid", "onScrolled, (lastVisibleItemPosition + pageSize) = ${(lastVisibleItemPosition + pageSize)}")

        if (!isLoading && totalItemCount - pageSize == lastVisibleItemPosition) {
            loadMoreItems()
        }
    }

    protected abstract fun loadMoreItems()
    abstract val isLastPage: Boolean
    abstract val isLoading: Boolean

}
