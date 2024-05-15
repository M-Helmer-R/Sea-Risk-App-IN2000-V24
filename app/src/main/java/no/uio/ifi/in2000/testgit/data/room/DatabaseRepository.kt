package no.uio.ifi.in2000.testgit.data.room

import kotlinx.coroutines.flow.Flow

interface DatabaseRepository{
     fun getAll() : Flow<List<City>>
     fun getPreLoaded() : Flow<List<City>>
     fun getFavorites() : Flow<List<City>>
     suspend fun saveCity(city : City)
     suspend fun deleteCity(city: City)
     fun setFavoriteById(city: City)
     fun removeFavoriteById(city: City)
     fun setFavoriteByName(name: String)
     fun removeFavoriteByName(name: String)
     fun isInDatabase(name : String) : Boolean
     fun isFavorite(name: String) : Boolean
     fun upsertCity(city: City)
     fun getPreloaded(): List<City>
     fun getFavourites(): Flow<List<City>>
     fun isCityFavorite(name: String): Boolean
     fun getCityByName(name: String): Any
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
    override fun setFavoriteByName(name: String){
        cityDao.setFavoriteByName(name)
    }

    override fun removeFavoriteByName(name: String){
        cityDao.removeFavoriteByName(name)
    }

    override fun isInDatabase(name : String) : Boolean {
        return if (cityDao.getCityByName(name) == null) {
            false
        } else {
            true
        }
    }

    override fun isFavorite(name: String) : Boolean {
        return cityDao.isCityFavorite(name)
    }

    override fun upsertCity(city: City) {
        TODO("Not yet implemented")
    }

    override fun getPreloaded(): List<City> {
        TODO("Not yet implemented")
    }

    override fun getFavourites(): Flow<List<City>> {
        TODO("Not yet implemented")
    }

    override fun isCityFavorite(name: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun getCityByName(name: String): Any {
        TODO("Not yet implemented")
    }

}