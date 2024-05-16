package no.uio.ifi.in2000.testgit.data.room

import android.util.Log
import kotlinx.coroutines.flow.Flow

//Repository for database operations
interface DatabaseRepository{
     fun getAll() : Flow<List<City>>
     fun getPreLoaded() : Flow<List<City>>
     fun getFavorites() : Flow<List<City>>
     suspend fun saveCity(city : City)
     suspend fun deleteCity(city: City)
     fun setFavoriteById(city: City)
     fun removeFavoriteById(city: City)
     suspend fun setFavoriteByName(name: String)
     suspend fun removeFavoriteByName(name: String)
     fun isInDatabase(name : String) : Boolean
     fun isFavorite(name: String) : Boolean
     fun isCustom(name: String) : Boolean

     suspend fun deleteCityByName(name: String)
}

class DatabaseRepositoryImpl (
    private val cityDao : CityDao
) : DatabaseRepository {
    override fun getAll() : Flow<List<City>>{
        return cityDao.getAll()
    }

    override fun getPreLoaded() : Flow<List<City>>{
        return cityDao.getPreloaded()
    }

    override fun getFavorites() : Flow<List<City>>{
        return cityDao.getFavourites()
    }

    override suspend fun saveCity(city : City){
        cityDao.upsertCity(city)
    }

    override suspend fun deleteCity(city: City){
        cityDao.deleteCity(city)
    }

    override fun setFavoriteById(city: City){
        cityDao.setFavoriteById(city.cityId)
    }

    override fun removeFavoriteById(city: City){
        cityDao.removeFavoriteById(city.cityId)
    }
    override suspend fun setFavoriteByName(name: String){
        Log.w("DatabaseRepositoryImpl", "setFavoriteByName:${name}")
        cityDao.setFavoriteByName(name)
    }

    override suspend fun removeFavoriteByName(name: String){
        Log.w("DatabaseRepositoryImpl", "remove favorite by name:${name}")
        cityDao.removeFavoriteByName(name)
    }

    override fun isInDatabase(name : String) : Boolean {
        return (cityDao.getCityByName(name) != null)
    }

    override fun isFavorite(name: String) : Boolean {
        return cityDao.isCityFavorite(name)
    }

    override fun isCustom(name: String) : Boolean {
        return cityDao.isCityCustom(name)
    }

    override suspend fun deleteCityByName(name: String) {
        cityDao.deleteCityByName(name)
    }
}