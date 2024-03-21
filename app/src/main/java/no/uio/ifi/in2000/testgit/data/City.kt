package no.uio.ifi.in2000.testgit.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class City (
    @PrimaryKey val cityId: Int,
    @ColumnInfo(name = "longitude") val lon : Double,
    @ColumnInfo(name = "lattitude") val lat : Double,
     )