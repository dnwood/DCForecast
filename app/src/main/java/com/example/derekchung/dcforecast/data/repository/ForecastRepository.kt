package com.example.derekchung.dcforecast.data.repository

import androidx.lifecycle.LiveData
import com.example.derekchung.dcforecast.data.db.unitlocalized.UnitSpecificCurrentWeatherEntry

interface ForecastRepository {
  suspend fun getCurrentWeather(metric: Boolean): LiveData<out UnitSpecificCurrentWeatherEntry>
}