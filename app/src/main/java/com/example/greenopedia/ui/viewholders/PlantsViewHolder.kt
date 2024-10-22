package com.example.greenopedia.ui.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.greenopedia.data.remote.responses.Data
import com.example.greenopedia.databinding.ItemPlantBinding
import com.example.greenopedia.R
import com.example.greenopedia.ui.OnItemClickListener

class PlantsViewHolder(private val binding: ItemPlantBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(plant: Data, listener: OnItemClickListener) {
        binding.apply {
            Glide.with(binding.root.context)
                .load(plant.imageUrl)
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_placeholder)
                .into(plantImageView)
            plantNameTextView.text = plant.commonName ?: "NA"
            plantYearTextView.text = plant.year?.toString()?:"NA"
            plantStatusTextView.text = plant.status?: "NA"
            itemView.setOnClickListener {
                listener.onItemClicked(plant)
            }
        }
    }
}