package com.example.greenopedia.data.remote.responses

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "plants")
data class Data(
    @PrimaryKey(autoGenerate = true)
    var pid: Int,
    val author: String?,
    val bibliography: String?,
    @SerializedName("common_name")
    val commonName: String?,
    val family: String?,
    @SerializedName("family_common_name")
    val familyCommonName: String?,
    val genus: String?,
    @SerializedName("genus_id")
    val genusId: Int?,
    @SerializedName("id")
    val plantId: Int?,
    @SerializedName("image_url")
    val imageUrl: String?,
    val links: Links?,
    val rank: String?,
    @SerializedName("scientific_name")
    val scientificName: String?,
    val slug: String?,
    val status: String?,
    val synonyms: List<String>?,
    val year: Int?,
    val timestamp: Long?
): Serializable