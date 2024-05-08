package no.uio.ifi.in2000.testgit.data.room

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [City::class],
    version = 1,
    exportSchema = true,
)
abstract class CityDatabase : RoomDatabase() {

    abstract val dao: CityDao

    companion object{

        @Volatile
        private var INSTANCE: CityDatabase? = null

        fun getDatabase(context : Context) : CityDatabase {
            Log.d("DATABASE", "Initialize")
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CityDatabase::class.java,
                    "cities.db"
                ).createFromAsset("database/cities100.db").build()
                INSTANCE= instance
                instance
            }
        }
    }
}

