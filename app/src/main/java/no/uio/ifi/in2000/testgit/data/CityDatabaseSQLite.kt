package no.uio.ifi.in2000.testgit.data

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Upsert

@Entity
data class City(
    val name : String,
    val lat : Double,
    val lon : Double,

    @PrimaryKey(autoGenerate = true)
    val id : Int,
)
data class City (
    @PrimaryKey val cityId: Int,
    @ColumnInfo(name = "name") val name : String,
    @ColumnInfo(name = "longitude") val lon : Double,
    @ColumnInfo(name = "lattitude") val lat : Double,
    @ColumnInfo(name = "favorite") val favorite : Boolean,
)

@Dao
interface CityDao {
    @Query("SELECT * FROM cities WHERE name == :name")
    fun findByName(name : String) : City

    @Query("SELECT * FROM cities WHERE cityId == :id")
    fun findByName(id : Int) : City

    @Upsert // @Insert (onConflict : Int = OnConflictStrategy.ABORT)
    suspend fun insertCity(city : City)

    @Delete
    suspend fun deleteCity(city : City)

    @Query("SELECT * FROM cities WHERE favorite == True")
    fun getFavourites(id : Int) : List<City>
}
class CityDatabaseSQLite {
}

