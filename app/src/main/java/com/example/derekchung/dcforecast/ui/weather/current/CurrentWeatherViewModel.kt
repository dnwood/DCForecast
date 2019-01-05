package com.example.derekchung.dcforecast.ui.weather.current

import androidx.lifecycle.ViewModel;
import com.example.derekchung.dcforecast.data.provider.UnitProvider
import com.example.derekchung.dcforecast.data.repository.ForecastRepository
import com.example.derekchung.dcforecast.internal.UnitSystem
import com.example.derekchung.dcforecast.internal.lazyDeferred

class CurrentWeatherViewModel(
    private val forecastRepository: ForecastRepository,
    unitProvider: UnitProvider
) : ViewModel() {
  private val unitSystem = unitProvider.getUnitSystem()

  val isMetric: Boolean
    get() = unitSystem == UnitSystem.METRIC

  val weather by lazyDeferred {
    forecastRepository.getCurrentWeather(isMetric)
  }
}
