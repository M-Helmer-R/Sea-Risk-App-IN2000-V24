package no.uio.ifi.in2000.testgit.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [City::class],
    version = 1,
)
abstract class CityDatabase : RoomDatabase() {

    abstract val dao: CityDao
}

