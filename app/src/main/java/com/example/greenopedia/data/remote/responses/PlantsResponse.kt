package com.example.greenopedia.data.remote.responses

import com.google.gson.annotations.SerializedName

data class PlantsResponse(
    @SerializedName("data")
    var plantsList: MutableList<Data>,
    var links: LinksX?,
    var meta: Meta
)