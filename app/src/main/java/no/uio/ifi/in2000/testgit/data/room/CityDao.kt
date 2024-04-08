package no.uio.ifi.in2000.testgit.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface CityDao {
    val near_dist: Int
        get() = 50

    @Query("SELECT * FROM cities WHERE name == :name")
    fun findByName(name : String) : City

    @Query("SELECT * FROM cities WHERE cityId == :id")
    fun findById(id : Int) : City

    @Upsert // @Insert (onConflict : Int = OnConflictStrategy.ABORT)
    suspend fun insertCity(city : City)

    @Delete
    suspend fun deleteCity(city : City)

    @Query("SELECT * FROM cities WHERE favorite == 1")
    fun getFavourites() : List<City>

    @Query("SELECT * FROM cities WHERE lattitude < 50 AND longitude < 50 ")
    fun getNearest() : List<City>

    //Set and remove favorites
    //@Query("Update ")

}