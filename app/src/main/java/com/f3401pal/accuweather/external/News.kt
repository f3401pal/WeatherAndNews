package com.f3401pal.accuweather.external

import com.f3401pal.accuweather.BuildConfig
import com.f3401pal.accuweather.external.model.TopHeadLine
import okhttp3.Interceptor
import retrofit2.http.GET
import retrofit2.http.Query

interface News {

    @GET("/$VERSION/top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String,
        @Query("page") page: Int = 0
    ): TopHeadLine


    companion object {
        const val BASE_URL = "https://newsapi.org"
        private const val VERSION = "v2"
        private const val QUERY_PARAM_NAME_API_KEY = "apiKey"

        val apiKeyInterceptor = Interceptor { chain ->
            val request = chain.request().newBuilder().apply {
                url(chain.request().url().newBuilder()
                    .addQueryParameter(QUERY_PARAM_NAME_API_KEY, BuildConfig.NEWS_API_KEY)
                    .build())
            }.build()

            chain.proceed(request)
        }
    }

}