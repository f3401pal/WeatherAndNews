package com.f3401pal.accuweather.presentation

import android.app.Application
import android.location.Geocoder
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.f3401pal.accuweather.domain.usecase.GetCurrentWeather
import com.f3401pal.accuweather.domain.usecase.GetTopHeadline
import com.f3401pal.accuweather.domain.usecase.GetWeatherForecast
import com.f3401pal.accuweather.domain.model.CurrentWeatherItem
import com.f3401pal.accuweather.domain.model.NewsItem
import com.f3401pal.accuweather.domain.model.WeatherForecastItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(
    application: Application,
    private val getCurrentWeather: GetCurrentWeather,
    private val getWeatherForecast: GetWeatherForecast,
    private val getTopHeadline: GetTopHeadline
) : AndroidViewModel(application) {

    private val curJob = SupervisorJob()
    private val bgContext: CoroutineScope = CoroutineScope(Dispatchers.Default + curJob)

    private val geoCoder: Geocoder = Geocoder(application)

    private val _currentWeather: MutableLiveData<CurrentWeatherItem> = MutableLiveData()
    val currentWeather: LiveData<CurrentWeatherItem> = _currentWeather

    private val _weatherForecast: MutableLiveData<WeatherForecastItem> = MutableLiveData()
    val weatherForecast: LiveData<WeatherForecastItem> = _weatherForecast

    private val _topHeadline: MutableLiveData<List<NewsItem>> = MutableLiveData()
    val topHeadline: LiveData<List<NewsItem>> = _topHeadline

    private val _error: MutableLiveData<String?> = MutableLiveData()
    val error: LiveData<String?> = _error

     init {
         loadAll(INITIAL_QUERY)
     }

    fun loadAll(query: String) {
        bgContext.launch {
            geoCoder.getFromLocationName(query, 1).firstOrNull()?.let {
                _currentWeather.postValue(getCurrentWeather.execute(it.locality))
                _weatherForecast.postValue(getWeatherForecast.execute(it.locality))
                _topHeadline.postValue(getTopHeadline.execute(it.countryCode))
            } ?: _error.postValue("Could not find city by \"$query\"")
        }
    }

    override fun onCleared() {
        super.onCleared()
        curJob.cancel()
    }

    companion object {
        private const val INITIAL_QUERY = "Calgary"
    }
}