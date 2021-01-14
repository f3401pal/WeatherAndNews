package com.f3401pal.accuweather.domain.usecase

import com.f3401pal.accuweather.external.News
import com.f3401pal.accuweather.external.model.toNewsItems
import com.f3401pal.accuweather.domain.model.NewsItem
import javax.inject.Inject

interface GetTopHeadline {

    suspend fun execute(country: String): List<NewsItem>

}

class GetTopHeadlineImpl @Inject constructor(
    private val newsApi: News
) : GetTopHeadline {

    override suspend fun execute(country: String): List<NewsItem> {
        return newsApi.getTopHeadlines(country).toNewsItems()
    }

}