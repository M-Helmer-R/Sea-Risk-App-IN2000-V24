package no.uio.ifi.in2000.testgit.ui.home

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import no.uio.ifi.in2000.testgit.data.room.City

@Dao
interface CityDao {
    //Repository

    @Upsert
    suspend fun upsertCity(city : City)

    @Delete
    suspend fun deleteCity(city : City)

    @Query("SELECT * FROM cities ORDER BY name ASC")
    fun getAll() : Flow<List<City>>

    @Query("SELECT * FROM cities WHERE favorite == 1 ORDER BY name ASC")
    fun getFavourites() : Flow<List<City>>

    @Query("SELECT * FROM cities WHERE customized == 1 ORDER BY name ASC")
    fun getCustoms() : Flow<List<City>>

    @Query("SELECT * FROM cities WHERE customized == 0 ORDER BY name ASC")
    fun getOriginals() : Flow<List<City>>

    @Query("UPDATE cities SET favorite = 1 WHERE cityId = :id")
    fun setFavoriteByID(id : Int)

    @Query("UPDATE cities SET favorite = 0 WHERE cityId = :id")
    fun removeFavoriteByID(id : Int)

    @Query("UPDATE cities SET favorite = 1 WHERE name = :name")
    fun setFavoriteByName(name : String)

    @Query("UPDATE cities SET favorite = 0 WHERE name = :name")
    fun removeFavoriteByName(name : String)
}