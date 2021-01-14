package com.f3401pal.accuweather.external.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TopHeadLine(
    val status: String,
    val totalResults: Int,
    val articles: List<NewsArticle>
)

@JsonClass(generateAdapter = true)
data class NewsArticle(
    val title: String,
    val description: String,
    val source: ArticleSource,
    val url: String,
    val urlToImage: String
)

@JsonClass(generateAdapter = true)
data class ArticleSource(
    val id: String?,
    val name: String
)