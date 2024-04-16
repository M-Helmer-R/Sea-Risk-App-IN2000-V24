package no.uio.ifi.in2000.testgit.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Query
import no.uio.ifi.in2000.testgit.data.room.City

@Entity(tableName = "cities")
data class City (
    @PrimaryKey(autoGenerate = true) // Default is true
    val cityId: Int = 0, // Autogenerates if null
    @ColumnInfo(name = "name") val name : String,
    @ColumnInfo(name = "longitude") val lon : Double,
    @ColumnInfo(name = "latitude") val lat : Double,
    @ColumnInfo(name = "favorite") var favorite : Boolean = false,
     )