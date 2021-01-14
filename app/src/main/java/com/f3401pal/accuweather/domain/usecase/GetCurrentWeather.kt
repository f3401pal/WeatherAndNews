package com.f3401pal.accuweather.domain.usecase

import com.f3401pal.accuweather.external.OpenWeather
import com.f3401pal.accuweather.external.model.toCurrentWeatherItem
import com.f3401pal.accuweather.domain.model.CurrentWeatherItem
import javax.inject.Inject

interface GetCurrentWeather {

    suspend fun execute(query: String): CurrentWeatherItem

}

class GetCurrentWeatherImpl @Inject constructor(
    private val openWeatherApi: OpenWeather
) : GetCurrentWeather {

    override suspend fun execute(query: String): CurrentWeatherItem {
        return openWeatherApi.getCurrentWeather(query).toCurrentWeatherItem()
    }

}