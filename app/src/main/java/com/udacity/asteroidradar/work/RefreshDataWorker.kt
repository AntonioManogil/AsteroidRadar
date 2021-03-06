package com.udacity.asteroidradar.work

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.database.getDatabase
import com.udacity.asteroidradar.repository.NasaRepository
import retrofit2.HttpException

class RefreshDataWorker(context: Context, params: WorkerParameters):
  CoroutineWorker(context, params){
  companion object{
    const val WORK_NAME = "RefreshDataWorker"
  }

  @RequiresApi(Build.VERSION_CODES.O)
  override suspend fun doWork(): Result {
    val database = getDatabase(context = applicationContext)
    val repository = NasaRepository(database)
    return try{
      repository.refreshNasa()
      repository.deleteOldAsteroids()
      Result.success()
    }catch(e: HttpException){
      Result.retry()
    }
  }
}