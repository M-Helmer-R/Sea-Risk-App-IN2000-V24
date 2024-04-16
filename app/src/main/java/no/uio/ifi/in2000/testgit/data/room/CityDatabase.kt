package no.uio.ifi.in2000.testgit.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [City::class],
    version = 1,
)
abstract class CityDatabase : RoomDatabase() {

    abstract val dao: CityDao
    /*
    init {
        Cities.entries.map {
            it -> dao.upsertCity(city = City( name = it.name, lon = it.lon, lat = it.lat))
        }
    }

     */
}

