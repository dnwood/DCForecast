package com.example.derekchung.dcforecast

import android.app.Application
import com.example.derekchung.dcforecast.data.db.ForecastDatabase
import com.example.derekchung.dcforecast.data.network.ApixuWeatherApiService
import com.example.derekchung.dcforecast.data.network.ConnectivityInterceptor
import com.example.derekchung.dcforecast.data.network.ConnectivityInterceptorImpl
import com.example.derekchung.dcforecast.data.network.WeatherNetworkDataSource
import com.example.derekchung.dcforecast.data.network.WeatherNetworkDataSourceImpl
import com.example.derekchung.dcforecast.data.repository.ForecastRepository
import com.example.derekchung.dcforecast.data.repository.ForecastRepositoryImpl
import com.example.derekchung.dcforecast.ui.weather.current.CurrentWeatherViewModelFactory
import com.jakewharton.threetenabp.AndroidThreeTen
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.androidModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class ForecastApplication : Application(), KodeinAware {
  override val kodein = Kodein.lazy {
    import(androidModule(this@ForecastApplication))

    bind() from singleton { ForecastDatabase(instance()) }
    bind() from singleton { instance<ForecastDatabase>().currentWeatherDao() }
    bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
    bind() from singleton { ApixuWeatherApiService(instance()) }
    bind<WeatherNetworkDataSource>() with singleton { WeatherNetworkDataSourceImpl(instance()) }
    bind<ForecastRepository>() with singleton { ForecastRepositoryImpl(instance(), instance()) }
    bind() from provider { CurrentWeatherViewModelFactory(instance()) }
  }

  override fun onCreate() {
    super.onCreate()
    AndroidThreeTen.init(this)
  }
}