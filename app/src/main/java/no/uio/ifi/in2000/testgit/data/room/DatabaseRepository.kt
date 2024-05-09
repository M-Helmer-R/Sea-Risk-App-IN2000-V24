package no.uio.ifi.in2000.testgit.data.room

import kotlinx.coroutines.flow.Flow

interface DatabaseRepository{
    fun getAll() : Flow<List<City>>

    fun getPreLoaded() : Flow<List<City>>

    fun getFavorites() : Flow<List<City>>

    suspend fun saveCity(city : City)
    suspend fun deleteCity(city: City)
    fun setFavorite(city: City)
    fun removeFavorite(city: City)
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

    override fun setFavorite(city: City){
        cityDao.setFavoriteByID(city.cityId)
    }

    override fun removeFavorite(city: City){
        cityDao.removeFavoriteByID(city.cityId)
    }
}