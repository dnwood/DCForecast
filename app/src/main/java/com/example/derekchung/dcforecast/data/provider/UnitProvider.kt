package com.example.derekchung.dcforecast.data.provider

import com.example.derekchung.dcforecast.internal.UnitSystem

interface UnitProvider {
  fun getUnitSystem(): UnitSystem
}