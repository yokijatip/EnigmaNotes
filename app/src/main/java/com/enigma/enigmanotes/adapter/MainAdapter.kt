package com.enigma.enigmanotes.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.enigma.enigmanotes.R
import com.enigma.enigmanotes.data.model.Notes
import com.enigma.enigmanotes.databinding.ItemCardMediumBinding

class MainAdapter(private val notesList: List<Notes>) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemCardMediumBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Notes) {
            binding.tvContentTitle.text = note.title
            binding.tvContentDescription.text = note.description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemCardMediumBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = notesList[position]
        holder.bind(note)
    }
}