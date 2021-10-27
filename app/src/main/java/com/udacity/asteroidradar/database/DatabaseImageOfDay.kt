package com.udacity.asteroidradar.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.udacity.asteroidradar.domain.ImageOfDay

@Entity
data class DatabaseImageOfDay constructor(
     @PrimaryKey
     val date: String,
     val explanation: String,
     val hdurl: String?,
     val media_type: String,
     val service_version: String,
     val title: String,
     val url: String
)

fun DatabaseImageOfDay.asDomainModel(): ImageOfDay {
     return ImageOfDay(
       date = this.date,
       explanation = this.explanation,
       hdurl = this.hdurl,
       media_type = this.media_type,
       service_version = this.service_version,
       title = this.title,
       url = this.url)
}
