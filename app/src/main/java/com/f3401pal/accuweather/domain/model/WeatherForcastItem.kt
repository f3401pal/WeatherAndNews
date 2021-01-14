package com.f3401pal.accuweather.domain.model

import com.f3401pal.accuweather.external.model.getIconUrl

data class DailyWeather(
    private val icon: String,
    val temp: Int
) {
    val iconUrl = getIconUrl(icon)
}

data class WeatherForecastItem(
    val weatherForecast: List<DailyWeather>
) {
    val numDays: Int by lazy { weatherForecast.size }
}