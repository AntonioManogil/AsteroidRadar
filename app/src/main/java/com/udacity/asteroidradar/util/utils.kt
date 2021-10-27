package com.udacity.asteroidradar.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
fun todayString(): String{
  return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
}
@RequiresApi(Build.VERSION_CODES.O)
fun inaweekString(): String{
  return LocalDateTime.now().plusDays(7).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
}