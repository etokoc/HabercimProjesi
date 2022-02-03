package com.ertugrulkoc.habercim.RecylerView

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ertugrulkoc.habercim.Model.ApiModel.NewsItem
import com.ertugrulkoc.habercim.databinding.RowItemBinding

class MyRecylerViewAdapter(val list: ArrayList<NewsItem>,val context: Context) :
    RecyclerView.Adapter<MyRecylerViewAdapter.MyViewHolder>() {
    class MyViewHolder(val binding: RowItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            RowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.rowTitle.setText(list.get(position).title)
        holder.binding.rowDescrp.setText(list.get(position).description)
        Glide.with(context).load(list[position].photo).centerCrop().into(holder.binding.rowImageView)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}