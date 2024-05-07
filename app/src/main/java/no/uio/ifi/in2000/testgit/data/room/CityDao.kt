package no.uio.ifi.in2000.testgit.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import no.uio.ifi.in2000.testgit.data.room.City

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
    fun setFavoriteByID(id: Int)

    @Query("UPDATE cities SET favorite = 0 WHERE cityId = :id")
    fun removeFavoriteByID(id: Int)
}