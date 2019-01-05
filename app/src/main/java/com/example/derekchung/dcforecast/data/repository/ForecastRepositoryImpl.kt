package com.example.derekchung.dcforecast.data.repository

import androidx.lifecycle.LiveData
import com.example.derekchung.dcforecast.data.db.CurrentWeatherDao
import com.example.derekchung.dcforecast.data.db.unitlocalized.UnitSpecificCurrentWeatherEntry
import com.example.derekchung.dcforecast.data.network.WeatherNetworkDataSource
import com.example.derekchung.dcforecast.data.network.response.CurrentWeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime
import java.util.Locale

class ForecastRepositoryImpl(
    private val currentWeatherDao: CurrentWeatherDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource
) : ForecastRepository {

  init {
    weatherNetworkDataSource.downloadedCurrentWeather.observeForever { newCurrentWeather ->
      persistFetchedCurrentWeather(newCurrentWeather)
    }
  }

  override suspend fun getCurrentWeather(
      metric: Boolean): LiveData<out UnitSpecificCurrentWeatherEntry> {
    return withContext(Dispatchers.IO) {
      initWeatherData()
      return@withContext if (metric) currentWeatherDao.getWeatherMetric()
      else currentWeatherDao.getWeatherImperial()
    }
  }

  private fun persistFetchedCurrentWeather(fetchedWeather: CurrentWeatherResponse) {
    GlobalScope.launch(Dispatchers.IO) {
      currentWeatherDao.upsert(fetchedWeather.currentWeatherEntry)
    }
  }

  private suspend fun initWeatherData() {
    if (isFetchCurrentNeeded(ZonedDateTime.now().minusHours(1)))
      fetchCurrentWeather()
  }

  private suspend fun fetchCurrentWeather() {
    weatherNetworkDataSource.fetchCurrentWeather(
        "Los Angeles", Locale.getDefault().language
    )
  }

  private fun isFetchCurrentNeeded(lastFetchTime: ZonedDateTime): Boolean {
    val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
    return lastFetchTime.isBefore(thirtyMinutesAgo)
  }
}