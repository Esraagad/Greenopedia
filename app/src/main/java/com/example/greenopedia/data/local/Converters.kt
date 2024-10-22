package com.example.greenopedia.data.local

import androidx.room.TypeConverter
import com.example.greenopedia.data.remote.responses.Links
import com.google.gson.Gson

class Converters {

    @TypeConverter
    fun fromLink(links:Links): String{
        return Gson().toJson(links)
    }

    @TypeConverter
    fun toLink(links: String): Links{
        return Gson().fromJson(links, Links::class.java)
    }

    @TypeConverter
    fun fromStringList(values: List<String>?): String {
        return values?.joinToString(",") ?: ""
    }

    @TypeConverter
    fun toStringList(values: String): List<String> {
        return values.split(",").map { it.trim() }
    }
}