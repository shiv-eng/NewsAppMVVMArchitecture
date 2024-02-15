package com.example.newsapp.data.model.topheadlines

import com.example.newsapp.data.local.entity.Source
import com.google.gson.annotations.SerializedName

data class ApiSource(
    @SerializedName("id") val id: String? = null,
    @SerializedName("name") val name: String = "",
)

fun ApiSource.toSourceEntity(): Source {
    return Source(id, name)
}
