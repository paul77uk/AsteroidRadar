package com.udacity.asteroidradar.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.ListItemBinding
import com.udacity.asteroidradar.main.MainFragment

class ItemAdapter : ListAdapter<Asteroid, ItemAdapter.ItemViewHolder>(DiffCallback) {

    class ItemViewHolder(private var binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
       fun bind(asteroid: Asteroid) {
           binding.asteroidFeed = asteroid
           binding.executePendingBindings()
       }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Asteroid>() {

        override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {

        return ItemViewHolder(ListItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ItemAdapter.ItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

}

