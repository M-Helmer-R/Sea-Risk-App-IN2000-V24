package no.uio.ifi.in2000.testgit.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

//Direct access object for cities table
@Dao
interface CityDao {
    @Upsert
    suspend fun upsertCity(city: City)

    @Delete
    suspend fun deleteCity(city: City)

    @Query("SELECT * FROM cities ORDER BY name ASC")
    fun getAll(): Flow<List<City>>

    @Query("SELECT * FROM cities WHERE favorite == 1 ORDER BY name ASC")
    fun getFavourites(): Flow<List<City>>

    @Query("SELECT * FROM cities WHERE customized == 1 ORDER BY name ASC")
    fun getCustoms(): Flow<List<City>>

    @Query("SELECT * FROM cities WHERE customized == 0 ORDER BY name ASC")
    fun getPreloaded(): Flow<List<City>>

    @Query("UPDATE cities SET favorite = 1 WHERE cityId = :id")
    fun setFavoriteById(id: Int)

    @Query("UPDATE cities SET favorite = 0 WHERE cityId = :id")
    fun removeFavoriteById(id: Int)

    @Query("UPDATE cities SET favorite = 1 WHERE name = :name")
    fun setFavoriteByName(name: String)

    @Query("UPDATE cities SET favorite = 0 WHERE cityId = :name")
    fun removeFavoriteByName(name: String)

    @Query("SELECT * FROM cities WHERE name = :name ORDER BY name ASC LIMIT 1")
    fun getCityByName(name: String): City?

    @Query("SELECT (favorite = 1) FROM cities WHERE name = :name LIMIT 1")
    fun isCityFavorite(name: String): Boolean
    @Query("SELECT (customized = 1) FROM cities WHERE name = :name LIMIT 1")
    fun isCityCustom(name: String): Boolean

    @Query("DELETE FROM cities WHERE name = :name")
    suspend fun deleteCityByName(name: String)

}