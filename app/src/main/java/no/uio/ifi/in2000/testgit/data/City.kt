package no.uio.ifi.in2000.testgit.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Query

@Entity(tableName = "cities")
data class City (
    @PrimaryKey val cityId: Int,
    @ColumnInfo(name = "name") val name : String,
    @ColumnInfo(name = "longitude") val lon : Double,
    @ColumnInfo(name = "lattitude") val lat : Double,
     )

interface CityDao {
    @Query("SELECT * FROM cities WHERE name == :name")
    fun findByName(name : String) : City

    @Query("SELECT * FROM cities WHERE cityId == :id")
    fun findByName(id : Int) : City
}


