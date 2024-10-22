package com.example.greenopedia.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.greenopedia.data.remote.responses.Data
import com.example.greenopedia.databinding.ItemPlantBinding
import com.example.greenopedia.ui.OnItemClickListener
import com.example.greenopedia.ui.viewholders.PlantsViewHolder

class PlantsListAdapter(val listener: OnItemClickListener) :
    RecyclerView.Adapter<PlantsViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<Data>() {
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem.plantId == newItem.plantId
        }

        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlantsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemPlantBinding = ItemPlantBinding.inflate(inflater, parent, false)
        return PlantsViewHolder(itemPlantBinding)

    }

    override fun onBindViewHolder(
        holder: PlantsViewHolder,
        position: Int
    ) {
        val plant = differ.currentList[position]
        holder.bind(plant, listener)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}