package no.uio.ifi.in2000.testgit.data.room

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import no.uio.ifi.in2000.testgit.ui.home.HomeEvent

class DatabaseRepository(
    private val cityDao : CityDao
){
    fun getAll() : Flow<List<City>>{
        return cityDao.getAll()
    }

    fun getPreLoaded() : Flow<List<City>>{
        return cityDao.getPreloaded()
    }

    fun getFavorites() : Flow<List<City>>{
        return cityDao.getFavourites()
    }

    suspend fun saveCity(city : City){
        cityDao.upsertCity(city)
    }

    suspend fun deleteCity(city: City){
        cityDao.deleteCity(city)
    }

    fun setFavorite(city: City){
        cityDao.setFavoriteByID(city.cityId)
    }

    fun removeFavorite(city: City){
        cityDao.removeFavoriteByID(city.cityId)
    }
}