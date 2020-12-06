package com.taegeon.portfolio.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.taegeon.portfolio.R
import com.taegeon.portfolio.data.Documents
import com.taegeon.portfolio.databinding.ImgListItemBinding
import com.taegeon.portfolio.listener.FragmentListener
import com.taegeon.portfolio.util.GlideApp

class ImgListAdapter (private val fragmentListener: FragmentListener) : RecyclerView.Adapter<ImgListAdapter.ViewHolder>(){
    var items: List<Documents> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewBinding = ImgListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        val listener = View.OnClickListener {
            fragmentListener.onChangeImgFragment(item.image_url, item.display_sitename, item.datetime)
        }
        holder.apply { bind(listener, item) }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(private val viewBinding: ImgListItemBinding) : RecyclerView.ViewHolder(viewBinding.root){
        fun bind(listener: View.OnClickListener, item: Documents){
            GlideApp.with(viewBinding.img.context)
                .load(item.image_url)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(viewBinding.img)
            viewBinding.root.setOnClickListener(listener)
        }
    }
}