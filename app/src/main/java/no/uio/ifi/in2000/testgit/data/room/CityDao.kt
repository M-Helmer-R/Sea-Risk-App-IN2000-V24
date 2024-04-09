package no.uio.ifi.in2000.testgit.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface CityDao {

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

    @Query("SELECT * FROM cities WHERE lattitude < dist AND longitude < dist ")
    fun getNearest(dist : Double
    ) : List<City>

    //Set and remove favorites
    //@Query("Update ")

}