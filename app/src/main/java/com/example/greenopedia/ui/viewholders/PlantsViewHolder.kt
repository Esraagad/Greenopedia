package com.example.greenopedia.ui.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.greenopedia.data.remote.responses.Data
import com.example.greenopedia.databinding.ItemPlantBinding
import com.example.greenopedia.R

class PlantsViewHolder(private val binding: ItemPlantBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(plant: Data) {
        binding.apply {
            Glide.with(binding.root.context)
                .load(plant.imageUrl)
                .placeholder(R.drawable.ic_placeholder)  // Shown while loading
                .error(R.drawable.ic_placeholder)
                .into(plantImageView)
            plantNameTextView.text = plant.commonName
            plantYearTextView.text = plant.year.toString()
            plantStatusTextView.text = plant.status
        }
    }
}