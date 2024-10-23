package com.example.greenopedia.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.greenopedia.R
import com.example.greenopedia.databinding.ItemFilterBinding
import com.example.greenopedia.ui.OnFilterItemClickedListener
import com.example.greenopedia.utils.Filter

class PlantsFiltersAdapter(
    private val filters: List<String>,
    private val itemClickListener: OnFilterItemClickedListener
) : RecyclerView.Adapter<PlantsFiltersAdapter.FiltersViewHolder>() {

    private var selectedPosition: Int = 0

   inner class FiltersViewHolder(private val binding: ItemFilterBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(filter: String, isSelected: Boolean, listener: OnFilterItemClickedListener) {
            binding.filterButton.apply {


                text = filter
                this.isSelected = isSelected
                setTextColor(
                    ContextCompat.getColor(
                        context,
                        if (isSelected) R.color.selected_text_color else R.color.unselected_text_color
                    )
                )
                background = ContextCompat.getDrawable(
                    context,
                    if (isSelected) R.drawable.button_selected_background else R.drawable.button_unselected_background
                )

                setOnClickListener {
                    val previousPosition = selectedPosition
                    selectedPosition = adapterPosition

                    // Trigger click listener to notify external components
                    listener.onFilterItemClicked(
                        Filter.fromDisplayName(filter),
                        previousPosition,
                        selectedPosition
                    )
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FiltersViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemPlantBinding = ItemFilterBinding.inflate(inflater, parent, false)
        return FiltersViewHolder(itemPlantBinding)
    }

    override fun onBindViewHolder(holder: FiltersViewHolder, position: Int) {
        holder.bind(filters[position], position == selectedPosition, itemClickListener)
    }

    override fun getItemCount(): Int = filters.size
}