package com.example.derekchung.dcforecast.data.network

import com.example.derekchung.dcforecast.data.network.response.CurrentWeatherResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://api.apixu.com/v1/"
const val API_KEY = "21dfa41645b6427fa0315056190401"

// https://api.apixu.com/v1/current.json?key=21dfa41645b6427fa0315056190401&q=Vancouver

interface ApixuWeatherApiService {

  @GET("current.json")
  fun getCurrentWeather(
      @Query("q") location: String,
      @Query("lang") languageCode: String = "en"
  ): Deferred<CurrentWeatherResponse>

  companion object {
    operator fun invoke(connectivityInterceptor: ConnectivityInterceptor): ApixuWeatherApiService {
      val requestInterceptor = Interceptor { chain ->
        val url = chain.request()
            .url()
            .newBuilder()
            .addQueryParameter("key", API_KEY)
            .build()

        val request = chain.request()
            .newBuilder()
            .url(url)
            .build()

        return@Interceptor chain.proceed(request)
      }

      val okHttpClient = OkHttpClient.Builder()
          .addInterceptor(requestInterceptor)
          .addInterceptor(connectivityInterceptor)
          .build()

      return Retrofit.Builder()
          .client(okHttpClient)
          .baseUrl(BASE_URL)
          .addCallAdapterFactory(CoroutineCallAdapterFactory())
          .addConverterFactory(GsonConverterFactory.create())
          .build()
          .create(ApixuWeatherApiService::class.java)
    }
  }
}