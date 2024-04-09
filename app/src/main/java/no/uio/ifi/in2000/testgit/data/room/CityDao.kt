package no.uio.ifi.in2000.testgit.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface CityDao {

    //Inserts city
    @Upsert // @Insert (onConflict : Int = OnConflictStrategy.ABORT)
    suspend fun upsertCity(city : City)

    @Delete
    suspend fun deleteCity(city : City)

    @Query("SELECT * FROM cities WHERE name == :name")
    fun findByName(name : String) :  Flow<City>

    @Query("SELECT * FROM cities WHERE cityId == :id")
    fun findById(id : Int) : Flow<City>

    @Query("SELECT * FROM cities WHERE favorite == 1 ORDER BY name ASC")
    fun getFavourites() : Flow<List<City>>

    //finds th
    @Query("SELECT * FROM cities WHERE haversine(:lat, :long, lattitude, longitude) <= :max_d ORDER BY haversine(:lat, :long, lattitude, longitude)")
    fun getNearest(long : Double, lat : Double, max_d : Double) : Flow<List<City>>

    //Set and remove favorites
    @Query("UPDATE cities SET favorite = 1 WHERE cityId = :id")
    fun setFavoriteByID(id : Int)

    @Query("UPDATE cities SET favorite = 0 WHERE cityId = :id")
    fun removeFavoriteByID(id : Int)

    @Query("UPDATE cities SET favorite = 1 WHERE name = :name")
    fun setFavoriteByName(name : String)

    @Query("UPDATE cities SET favorite = 0 WHERE name = :name")
    fun removeFavoriteByName(name : String)
}