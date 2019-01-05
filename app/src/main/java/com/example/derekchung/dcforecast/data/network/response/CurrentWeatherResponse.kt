package com.example.derekchung.dcforecast.data.network.response

import com.example.derekchung.dcforecast.data.db.entity.CurrentWeatherEntry
import com.example.derekchung.dcforecast.data.db.entity.Location
import com.google.gson.annotations.SerializedName

data class CurrentWeatherResponse(
    val location: Location,
    @SerializedName("current")
    val currentWeatherEntry: CurrentWeatherEntry)