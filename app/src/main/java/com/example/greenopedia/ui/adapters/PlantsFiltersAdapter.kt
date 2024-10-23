package com.example.greenopedia.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.greenopedia.databinding.ItemFilterBinding
import com.example.greenopedia.ui.OnFilterItemClickedListener
import com.example.greenopedia.ui.viewholders.FiltersViewHolder

class PlantsFiltersAdapter(
    private val filters: List<String>,
    private val itemClickListener: OnFilterItemClickedListener
) : RecyclerView.Adapter<FiltersViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FiltersViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemPlantBinding = ItemFilterBinding.inflate(inflater, parent, false)
        return FiltersViewHolder(itemPlantBinding)
    }

    override fun onBindViewHolder(holder: FiltersViewHolder, position: Int) {
        holder.bind(filters[position], itemClickListener)
    }

    override fun getItemCount(): Int = filters.size
}