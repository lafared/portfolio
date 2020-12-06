package com.taegeon.portfolio.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.taegeon.portfolio.R
import com.taegeon.portfolio.data.Documents
import com.taegeon.portfolio.databinding.ImgListItemBinding

class ImgListAdapter () : RecyclerView.Adapter<ImgListAdapter.ViewHolder>(){
    var items: List<Documents> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewBinding = ImgListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        val listener = View.OnClickListener {
            Navigation.findNavController(holder.itemView).navigate(R.id.action_mainfragment_to_imgviewfragment)
        }
        holder.apply { bind(listener, item) }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(private val viewBinding: ImgListItemBinding) : RecyclerView.ViewHolder(viewBinding.root){
        fun bind(listener: View.OnClickListener, item: Documents){
            viewBinding.test.text = item.datetime
            viewBinding.root.setOnClickListener(listener)
        }
    }
}