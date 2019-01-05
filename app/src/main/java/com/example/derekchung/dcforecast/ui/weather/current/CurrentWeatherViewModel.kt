package com.example.derekchung.dcforecast.ui.weather.current

import androidx.lifecycle.ViewModel;
import com.example.derekchung.dcforecast.data.repository.ForecastRepository
import com.example.derekchung.dcforecast.internal.UnitSystem
import com.example.derekchung.dcforecast.internal.lazyDeferred

class CurrentWeatherViewModel(
    private val forecastRepository: ForecastRepository
) : ViewModel() {
  private val unitSystem = UnitSystem.METRIC //  get from settings later

  val isMetric: Boolean
    get() = unitSystem == UnitSystem.METRIC

  val weather by lazyDeferred {
    forecastRepository.getCurrentWeather(isMetric)
  }
}
