package com.example.portfolio.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.portfolio.R
import com.example.portfolio.databinding.ImgListItemBinding
import com.example.portfolio.viewmodel.TmpItem

class ImgListAdapter (private val items: ArrayList<TmpItem>) : RecyclerView.Adapter<ImgListAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewBinding = ImgListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        val listener = View.OnClickListener { it ->
            Toast.makeText(it.context, "position : ${position}", Toast.LENGTH_SHORT).show()
            Navigation.findNavController(holder.itemView).navigate(R.id.action_mainfragment_to_imgviewfragment)
        }
        holder.apply { bind(listener, item) }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(private val viewBinding: ImgListItemBinding) : RecyclerView.ViewHolder(viewBinding.root){
        fun bind(listener: View.OnClickListener, item: TmpItem){
            viewBinding.tmpText.text = item.text
            viewBinding.root.setOnClickListener(listener)
        }
    }
}