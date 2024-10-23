package com.example.greenopedia.utils

enum class Filter(val id: String, val displayName: String) {
    ALL("all", "All"),
    PALESTINE("pal", "Palestine"),
    SUDAN("sud", "Sudan"),
    MYANMAR("mya", "Myanmar"),
    TRANSCAUCASUS("tcs", "Transcaucasus"),
    UZBEKISTAN("uzb", "Uzbekistan");

    companion object {
        fun fromDisplayName(displayName: String): Filter? {
            return Filter.entries.find { it.displayName == displayName }
        }
    }
}