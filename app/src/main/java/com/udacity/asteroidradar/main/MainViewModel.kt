package com.udacity.asteroidradar.main

import android.annotation.SuppressLint
import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.udacity.asteroidradar.database.AsteroidFilter
import com.udacity.asteroidradar.database.getDatabase
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.repository.NasaRepository
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class MainViewModel(application: Application) : AndroidViewModel(application) {
  private val database = getDatabase(application)
  private val nasaRepository = NasaRepository(database)

  private val _navigateToSelectedAsteroid = MutableLiveData<Asteroid>()
  val navigateToSelectedAsteroid : LiveData<Asteroid> get() = _navigateToSelectedAsteroid

  private val _asteroids = MutableLiveData<List<Asteroid>>()
  val asteroids : LiveData<List<Asteroid>> get() = _asteroids

  init{
    viewModelScope.launch{
      nasaRepository.refreshNasa()
    }
    getAsteroids(AsteroidFilter.SHOW_WEEK)
  }
  private fun getAsteroids(filter: AsteroidFilter){
    viewModelScope.launch {
      _asteroids.value = nasaRepository.getAsteroids(filter)
    }
  }
  val imageOfDay = nasaRepository.imageOfDay
  fun displayAsteroidDetails(asteroid: Asteroid){
    _navigateToSelectedAsteroid.value = asteroid }
  @SuppressLint("NullSafeMutableLiveData")
  fun displayAsteroidDetailsComplete(){
    _navigateToSelectedAsteroid.value = null }
  fun updateFilter(filter: AsteroidFilter){
    getAsteroids(filter)
  }

  class Factory(private val app: Application) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
      if(modelClass.isAssignableFrom(MainViewModel::class.java)){
        return MainViewModel(app) as T
      }
      throw IllegalArgumentException("unable to construct viewModel class")
    }
  }
}