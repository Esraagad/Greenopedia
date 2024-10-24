package com.example.greenopedia.data.remote.responses

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "plants")
data class Data(
    val author: String? = "",
    val bibliography: String? = "",
    @SerializedName("common_name")
    val commonName: String? = "",
    val family: String? = "",
    @SerializedName("family_common_name")
    val familyCommonName: String? = "",
    val genus: String? = "",
    @SerializedName("genus_id")
    val genusId: Int? = 0,
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    val plantId: Int? = 0,
    @SerializedName("image_url")
    val imageUrl: String? = "",
    val links: Links? = Links(),
    val rank: String? = "",
    @SerializedName("scientific_name")
    val scientificName: String? = "",
    val slug: String? = "",
    val status: String? = "",
    val synonyms: List<String>? = listOf(),
    val year: Int? = 0,
    val timestamp: Long? = 0,
    var zone: String? = ""
) : Serializable