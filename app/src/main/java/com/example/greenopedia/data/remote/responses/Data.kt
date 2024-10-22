package com.example.greenopedia.data.remote.responses

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "plants")
data class Data(
    @PrimaryKey(autoGenerate = true)
    var pid: Int? = 0,
    val author: String = "NA",
    val bibliography: String = "NA",
    @SerializedName("common_name")
    val commonName: String = "NA",
    val family: String = "NA",
    @SerializedName("family_common_name")
    val familyCommonName: String = "NA",
    val genus: String = "NA",
    @SerializedName("genus_id")
    val genusId: Int = 0,
    @SerializedName("id")
    val plantId: Int = 0,
    @SerializedName("image_url")
    val imageUrl: String = "NA",
    val links: Links? = null,
    val rank: String = "NA",
    @SerializedName("scientific_name")
    val scientificName: String = "NA",
    val slug: String = "NA",
    val status: String = "NA",
    val synonyms: List<String>? = null,
    val year: Int = 0,
    val timestamp: Long = 0
)