package com.f3401pal.accuweather.domain.model

import com.f3401pal.accuweather.external.model.getIconUrl

data class CurrentWeatherItem(
    val location: String,
    val temp: Int,
    val maxTemp: Int,
    val minTemp: Int,
    val mainWeather: String,
    private val iconId: String
) {
    val iconUrl = getIconUrl(iconId)
}