package com.f3401pal.accuweather.di

import com.f3401pal.accuweather.external.News
import com.f3401pal.accuweather.external.OpenWeather
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    private val moshiConverterFactory = MoshiConverterFactory.create()

    @Provides
    fun provideOpenWeatherApi(): OpenWeather =
        Retrofit.Builder()
            .baseUrl(OpenWeather.BASE_URL)
            .addConverterFactory(moshiConverterFactory)
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(OpenWeather.apiKeyInterceptor)
                    .build()
            )
            .build().create(OpenWeather::class.java)

    @Provides
    fun provideNewsApi(): News =
        Retrofit.Builder()
            .baseUrl(News.BASE_URL)
            .addConverterFactory(moshiConverterFactory)
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(News.apiKeyInterceptor)
                    .build()
            )
            .build().create(News::class.java)
}