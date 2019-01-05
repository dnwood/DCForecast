package com.example.derekchung.dcforecast.data.network

import android.content.Context
import android.net.ConnectivityManager
import com.example.derekchung.dcforecast.internal.NoConnectivityException
import okhttp3.Interceptor.Chain
import okhttp3.Response

class ConnectivityInterceptorImpl(
    context: Context
) : ConnectivityInterceptor {

  private val appContext = context.applicationContext

  override fun intercept(chain: Chain): Response {
    if (!isOnline()) throw NoConnectivityException()
    return chain.proceed(chain.request())
  }

  private fun isOnline(): Boolean {
    val connectivityManager = appContext.getSystemService(Context.CONNECTIVITY_SERVICE)
        as ConnectivityManager
    val networkInfo = connectivityManager.activeNetworkInfo
    return networkInfo != null && networkInfo.isConnected
  }
}