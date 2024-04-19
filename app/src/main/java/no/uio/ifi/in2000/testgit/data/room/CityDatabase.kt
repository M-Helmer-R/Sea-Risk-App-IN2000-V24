package no.uio.ifi.in2000.testgit.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import no.uio.ifi.in2000.testgit.ui.home.CityDao

@Database(
    entities = [City::class],
    version = 1,
    exportSchema = true,
)
abstract class CityDatabase : RoomDatabase() {

    abstract val dao: CityDao

}

