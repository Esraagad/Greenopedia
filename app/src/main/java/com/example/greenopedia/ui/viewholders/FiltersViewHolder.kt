package com.example.greenopedia.ui.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.example.greenopedia.databinding.ItemFilterBinding
import com.example.greenopedia.ui.OnFilterItemClickedListener
import com.example.greenopedia.utils.Filter

class FiltersViewHolder(private val binding: ItemFilterBinding) :
    RecyclerView.ViewHolder(binding.root) {

    var selectedPosition: Int = 0
    var currentPosition: Int = 0

    fun bind(
        filter: String,
        itemClickListener: OnFilterItemClickedListener
    ) {
        binding.filterButton.apply {
            text = filter
            val filter = Filter.fromDisplayName(filter)

            setOnClickListener {
                currentPosition = adapterPosition
                itemClickListener.onFilterItemClicked(filter, selectedPosition, currentPosition)
                selectedPosition = currentPosition
            }
        }
    }
}
