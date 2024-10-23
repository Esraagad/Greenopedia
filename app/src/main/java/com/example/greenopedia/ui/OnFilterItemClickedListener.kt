package com.example.greenopedia.ui

import com.example.greenopedia.utils.Filter


interface OnFilterItemClickedListener {
    fun onFilterItemClicked(filter: Filter?, oldPosition: Int, currentPosition: Int)
}