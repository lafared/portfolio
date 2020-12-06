package com.taegeon.portfolio.listener

import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

const val REMAIN_ITEM_CNT_TO_LOAD_MORE: Int = 10

class ImgListScrollListener(
    val loadMore: () -> Unit,
    val layoutManager: GridLayoutManager
) : RecyclerView.OnScrollListener(), SearchStatusListener{

    private var isSearchable = true

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val pos = layoutManager.findLastCompletelyVisibleItemPosition()
        val itemCount = layoutManager.itemCount
        Log.d("ImgListScrollListener", "pos : $pos, itemCount : $itemCount, isSearchable : $isSearchable")
        if (itemCount - pos < REMAIN_ITEM_CNT_TO_LOAD_MORE && isSearchable) {
            loadMore()
        }
    }

    override fun onSearchStatusChange(isSearchable: Boolean) {
        this.isSearchable = isSearchable
    }
}