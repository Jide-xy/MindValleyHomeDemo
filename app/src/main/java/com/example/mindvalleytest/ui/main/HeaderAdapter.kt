package com.example.mindvalleytest.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mindvalleytest.databinding.ItemLayoutHeaderBinding

class HeaderAdapter : RecyclerView.Adapter<HeaderAdapter.HeaderViewHolder>() {

    inner class HeaderViewHolder(binding: ItemLayoutHeaderBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeaderViewHolder {
        return HeaderViewHolder(
            ItemLayoutHeaderBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount() = 1

    override fun onBindViewHolder(holder: HeaderViewHolder, position: Int) {

    }
}