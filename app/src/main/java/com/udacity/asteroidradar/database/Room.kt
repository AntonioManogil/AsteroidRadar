package com.udacity.asteroidradar.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

enum class AsteroidFilter(val value: String) {
  SHOW_WEEK("week"), SHOW_DAY("day"), SHOW_ALL("all")
}

@Dao
interface AsteroidRadarDao{
  @Query("select * from databaseimageofday where date = :date")
  fun getImageOfDay(date: String): LiveData<DatabaseImageOfDay>
  @Query("select * from databaseasteroid where closeApproachDate >= :start_date and closeApproachDate <= :end_date order by closeApproachDate")
  suspend fun getWeekAsteroids(start_date: String, end_date: String): List<DatabaseAsteroid>
  @Query("select * from databaseasteroid where closeApproachDate = :today_date order by closeApproachDate")
  suspend fun getTodayAsteroids(today_date: String): List<DatabaseAsteroid>
  @Query("select * from databaseasteroid order by closeApproachDate")
  suspend fun getAllAsteroids(): List<DatabaseAsteroid>
  @Query("delete from databaseasteroid where closeApproachDate < :today_date")
  suspend fun deleteOldAsteroids(today_date: String)
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertRecord(databaseImageOfDay: DatabaseImageOfDay)
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertAll(vararg databaseAsteroid: DatabaseAsteroid)
}

@Database(entities = [DatabaseImageOfDay::class, DatabaseAsteroid::class], version = 3)
abstract class AsteroidRadarDatabase: RoomDatabase(){
  abstract val asteroidRadarDao: AsteroidRadarDao
}

private lateinit var INSTANCE: AsteroidRadarDatabase

fun getDatabase(context: Context): AsteroidRadarDatabase{
  synchronized(AsteroidRadarDatabase::class.java){
    if(!::INSTANCE.isInitialized){
      INSTANCE = Room.databaseBuilder(context.applicationContext, AsteroidRadarDatabase::class.java, "asteroidradar")
        .build()
    }
  }
  return INSTANCE
}