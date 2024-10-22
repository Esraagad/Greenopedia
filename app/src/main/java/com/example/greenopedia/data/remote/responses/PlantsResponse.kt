package com.example.greenopedia.data.remote.responses

data class PlantsResponse(
    val plantsList: List<Data>,
    val links: LinksX,
    val meta: Meta
)