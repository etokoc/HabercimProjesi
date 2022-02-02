package com.ertugrulkoc.habercim.RecylerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ertugrulkoc.habercim.databinding.ActivityMainBinding

class MyRecylerViewAdapter : RecyclerView.Adapter<MyRecylerViewAdapter.MyViewHolder>() {
    class MyViewHolder(val binding: ActivityMainBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ActivityMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}