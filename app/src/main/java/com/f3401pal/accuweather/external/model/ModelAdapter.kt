package com.f3401pal.accuweather.external.model

import com.f3401pal.accuweather.domain.model.CurrentWeatherItem
import com.f3401pal.accuweather.domain.model.DailyWeather
import com.f3401pal.accuweather.domain.model.NewsItem
import com.f3401pal.accuweather.domain.model.WeatherForecastItem
import java.time.Instant

fun LocalWeatherInstant.toCurrentWeatherItem() = CurrentWeatherItem(
    location = name,
    temp = main.temp.toInt(),
    maxTemp = main.maxTemp.toInt(),
    minTemp = main.minTemp.toInt(),
    mainWeather = weather.first().main,
    iconId = weather.first().icon
)

fun WeatherInstant.toDailyWeather() = DailyWeather(
    icon = weather.first().icon,
    temp = main.temp.toInt()
)

fun WeatherForecast.toWeatherForecastItem(numDays: Int = 3): WeatherForecastItem {
    val result = mutableListOf<DailyWeather>()
    var nextTimeStamp = Instant.now().epochSecond + ONE_DAY_IN_SECONDS
    list.forEach { instant ->
        if (result.size == numDays) return@forEach
        if(instant.dt >= nextTimeStamp) {
            result.add(instant.toDailyWeather())
            nextTimeStamp += ONE_DAY_IN_SECONDS
        }
    }
    return WeatherForecastItem(result)
}

fun TopHeadLine.toNewsItems(): List<NewsItem> = articles.map {
    NewsItem(
        title = it.title,
        source = it.source.name,
        imageUrl = it.urlToImage
    )
}

fun getIconUrl(iconId: String) = "https://openweathermap.org/img/wn/$iconId@4x.png"

private const val ONE_DAY_IN_SECONDS = 86400L