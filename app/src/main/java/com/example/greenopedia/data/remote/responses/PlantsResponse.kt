package com.example.greenopedia.data.remote.responses

import com.google.gson.annotations.SerializedName

data class PlantsResponse(
    @SerializedName("data")
    val plantsList: MutableList<Data>,
    val links: LinksX,
    val meta: Meta
)