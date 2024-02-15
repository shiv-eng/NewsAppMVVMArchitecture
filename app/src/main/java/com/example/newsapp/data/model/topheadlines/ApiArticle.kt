package com.example.newsapp.data.model.topheadlines

import com.example.newsapp.data.local.entity.Article
import com.google.gson.annotations.SerializedName

data class ApiArticle(
    @SerializedName("title") val title: String = "",
    @SerializedName("description") val description: String?,
    @SerializedName("url") val url: String = "",
    @SerializedName("urlToImage") val imageUrl: String = "",
    @SerializedName("source") val apiSource: ApiSource,
)

fun ApiArticle.toArticleEntity(country: String) = Article(
    title = title,
    description = description ?: "",
    url = url,
    imageUrl = imageUrl ?: "",
    country = country,
    source = apiSource.toSourceEntity(),
)

fun List<ApiArticle>.apiArticleListToArticleList(country: String): List<Article> {
    val list = mutableListOf<Article>()
    forEach { apiArticle ->
        list.add(apiArticle.toArticleEntity(country))
    }
    return list
}

fun ApiArticle.toArticleLanguage(language: String): Article {
    return Article(
        title = title,
        description = description ?: "",
        url = url,
        imageUrl = imageUrl ?: "",
        language = language,
        source = apiSource.toSourceEntity(),
    )
}
