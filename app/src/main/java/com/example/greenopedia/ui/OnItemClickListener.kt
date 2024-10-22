package com.example.greenopedia.ui

import com.example.greenopedia.data.remote.responses.Data

interface OnItemClickListener {
    fun onItemClicked(plant: Data)
}