package com.f3401pal.accuweather.external.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherInstant(
    val weather: List<Weather>,
    val main: Temperature,
    val dt: Long
)

@JsonClass(generateAdapter = true)
data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

@JsonClass(generateAdapter = true)
data class Temperature(
    val temp: Float,
    @Json(name = "feels_like") val feelsLike: Float,
    @Json(name = "temp_min") val minTemp: Float,
    @Json(name = "temp_max") val maxTemp: Float,
    val pressure: Int,
    val humidity: Int
)

@JsonClass(generateAdapter = true)
data class LocalWeatherInstant(
    val weather: List<Weather>,
    val main: Temperature,
    val name: String,
    val dt: Long
)

@JsonClass(generateAdapter = true)
data class WeatherForecast(
    val list: List<WeatherInstant>
)
