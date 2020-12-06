package com.taegeon.portfolio.adapter

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.taegeon.portfolio.data.Documents

object DataBindingAdapters {
    @JvmStatic
    @BindingAdapter("bind_img_n_list")
    fun BindImgNList(recyclerView: RecyclerView, items: MutableLiveData<ArrayList<Documents>>) {
        if (items.value != null && items.value!!.isNotEmpty()) {
            val adapter = recyclerView.adapter as? ImgListAdapter ?: ImgListAdapter().apply { recyclerView.adapter = this }
            adapter.items = items.value!!
            adapter.notifyDataSetChanged()
            recyclerView.visibility = View.VISIBLE
        } else {
            recyclerView.visibility = View.GONE
        }
    }

    @JvmStatic
    @BindingAdapter("bind_img_n_emptytxt")
    fun BindImgNEmptyTxt(textView: TextView, items: MutableLiveData<ArrayList<Documents>>) {
        if (items.value != null && items.value!!.isNotEmpty()) {
            textView.visibility = View.GONE
        } else {
            textView.visibility = View.VISIBLE
        }
    }
}