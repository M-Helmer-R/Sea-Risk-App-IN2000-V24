package no.uio.ifi.in2000.testgit.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cities")
data class City (

 @PrimaryKey(autoGenerate = true)
 @ColumnInfo(name = "cityId")
 val cityId: Int = 0, // Autogenerates if null

 @ColumnInfo(name = "name")
 val name : String,

 @ColumnInfo(name = "customized")
 var customized : Int = 0,

 @ColumnInfo(name = "favorite")
 var favorite : Int = 0,

 @ColumnInfo(name = "longitude")
 val lon : Double,

 @ColumnInfo(name = "latitude")
 val lat : Double,

 )