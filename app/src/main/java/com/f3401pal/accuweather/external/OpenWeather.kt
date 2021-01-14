package com.f3401pal.accuweather.external

import com.f3401pal.accuweather.BuildConfig
import com.f3401pal.accuweather.external.model.LocalWeatherInstant
import com.f3401pal.accuweather.external.model.WeatherForecast
import okhttp3.Interceptor
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeather {

    @GET("/data/$VERSION/weather")
    suspend fun getCurrentWeather(@Query("q") query: String, @Query("units") units: String = UNIT_METRIC): LocalWeatherInstant

    @GET("/data/$VERSION/forecast")
    suspend fun getWeatherForecast(@Query("q") query: String, @Query("units") units: String = UNIT_METRIC): WeatherForecast

    companion object {
        const val BASE_URL = "https://api.openweathermap.org"
        private const val VERSION = "2.5"
        private const val QUERY_PARAM_NAME_API_KEY = "appid"

        private const val UNIT_METRIC = "metric"

        val apiKeyInterceptor = Interceptor { chain ->
            val request = chain.request().newBuilder().apply {
                url(chain.request().url().newBuilder()
                    .addQueryParameter(QUERY_PARAM_NAME_API_KEY, BuildConfig.OPEN_WEATHER_API_KEY)
                    .build())
            }.build()

            chain.proceed(request)
        }
    }

}