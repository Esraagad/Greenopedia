package com.example.greenopedia.ui

import com.example.greenopedia.data.remote.responses.Data

interface OnPlantItemClickedListener {
    fun onPlantItemClicked(plant: Data)
}