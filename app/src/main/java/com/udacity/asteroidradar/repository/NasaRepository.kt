package com.udacity.asteroidradar.repository

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.AsteroidFilter
import com.udacity.asteroidradar.database.AsteroidRadarDatabase
import com.udacity.asteroidradar.database.asDomainModel
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.network.Network
import com.udacity.asteroidradar.network.asDatabaseModel
import com.udacity.asteroidradar.util.inaweekString
import com.udacity.asteroidradar.util.todayString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject


@RequiresApi(Build.VERSION_CODES.O)
class NasaRepository(private val database: AsteroidRadarDatabase) {
  companion object{
    private const val API_KEY = "24fZsZnet2eyRqqkHg21MbTo4gzUGDbMXIWUavR5"
  }
  @RequiresApi(Build.VERSION_CODES.O)
  val imageOfDay = Transformations.map(database.asteroidRadarDao.getImageOfDay(todayString())){
    it?.asDomainModel()
  }

  suspend fun refreshNasa(){
    withContext(Dispatchers.IO) {
      try {
        val networkImageOfDay = Network.imageOfDayService.getNetworkImageOfDayAsync(API_KEY).await()
        val databaseImageOfDay = networkImageOfDay.asDatabaseModel()
        database.asteroidRadarDao.insertRecord(databaseImageOfDay)
        val networkAsteroidString = Network.asteroidsService.getAsteroidsAsync(todayString(), inaweekString(), API_KEY).await()
        val networkAsteroids = parseAsteroidsJsonResult(JSONObject(networkAsteroidString))
        database.asteroidRadarDao.insertAll(*networkAsteroids.asDatabaseModel())
      }catch(e: Exception){
        Log.e("GetImage", e.stackTrace.toString())
        Log.e("GetImage", e.message.toString())
      }
    }
  }
  suspend fun getAsteroids(filter: AsteroidFilter): List<Asteroid> {
    val databaseAsteroid =
    when (filter) {
      AsteroidFilter.SHOW_WEEK -> database.asteroidRadarDao.getWeekAsteroids(todayString(), inaweekString())
      AsteroidFilter.SHOW_DAY -> database.asteroidRadarDao.getTodayAsteroids(todayString())
      AsteroidFilter.SHOW_ALL -> database.asteroidRadarDao.getAllAsteroids()
    }
    return databaseAsteroid.asDomainModel()
  }
  suspend fun deleteOldAsteroids() {
    database.asteroidRadarDao.deleteOldAsteroids(todayString())
  }
}