package com.udacity.asteroidradar.network

import com.squareup.moshi.JsonClass
import com.udacity.asteroidradar.database.DatabaseImageOfDay
@JsonClass(generateAdapter = true)
data class NetworkImageOfDay (
    val date: String,
    val explanation: String,
    val hdurl: String?,
    val media_type: String,
    val service_version: String,
    val title: String,
    val url: String)

fun NetworkImageOfDay.asDatabaseModel(): DatabaseImageOfDay{
    return DatabaseImageOfDay(
            date =  this.date,
            explanation = this.explanation,
            hdurl = this.hdurl,
            media_type = this.media_type,
            service_version = this.service_version,
            title = this.title,
            url = this.url)
}


