package com.example.greenopedia.data.remote.responses

import com.google.gson.annotations.SerializedName

data class Data(
    val author: String,
    val bibliography: String,
    @SerializedName("common_name")
    val commonName: String,
    val family: String,
    @SerializedName("family_common_name")
    val familyCommonName: String,
    val genus: String,
    @SerializedName("genus_id")
    val genusId: Int,
    val id: Int,
    @SerializedName("image_url")
    val imageUrl: String,
    val links: Links,
    val rank: String,
    @SerializedName("scientific_name")
    val scientificName: String,
    val slug: String,
    val status: String,
    val synonyms: List<String>,
    val year: Int
)