package com.f3401pal.accuweather.di

import com.f3401pal.accuweather.domain.usecase.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainModule {

    @Binds
    abstract fun bindGetCurrentWeather(getCurrentWeather: GetCurrentWeatherImpl): GetCurrentWeather

    @Binds
    abstract fun bindGetWeatherForecast(getWeatherForecast: GetWeatherForecastImpl): GetWeatherForecast

    @Binds
    abstract fun bindGetTopHeadline(getTopHeadline: GetTopHeadlineImpl): GetTopHeadline
}