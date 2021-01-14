package com.f3401pal.accuweather.domain.usecase

import com.f3401pal.accuweather.external.OpenWeather
import com.f3401pal.accuweather.external.model.toWeatherForecastItem
import com.f3401pal.accuweather.domain.model.WeatherForecastItem
import javax.inject.Inject

interface GetWeatherForecast {

    suspend fun execute(query: String): WeatherForecastItem

}

class GetWeatherForecastImpl @Inject constructor(
    private val openWeatherApi: OpenWeather
) : GetWeatherForecast {

    override suspend fun execute(query: String): WeatherForecastItem {
        return openWeatherApi.getWeatherForecast(query).toWeatherForecastItem()
    }

}