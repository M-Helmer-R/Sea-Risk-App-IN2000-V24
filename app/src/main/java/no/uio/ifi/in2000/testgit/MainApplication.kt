package no.uio.ifi.in2000.testgit

import android.app.Application
import no.uio.ifi.in2000.testgit.data.location.LocationRepository
import no.uio.ifi.in2000.testgit.data.location.LocationRepositoryImpl
import no.uio.ifi.in2000.testgit.data.room.CityDatabase
import no.uio.ifi.in2000.testgit.data.room.DatabaseRepository
import no.uio.ifi.in2000.testgit.data.room.DatabaseRepositoryImpl

//Application to extend the application context and
// initialize the database- and locationrepositories
class MainApplication : Application() {
    lateinit var databaseRepository: DatabaseRepository
    lateinit var locationRepository: LocationRepository
    override fun onCreate() {
        super.onCreate()
        val db = CityDatabase.getDatabase(this.applicationContext)
        databaseRepository = DatabaseRepositoryImpl(cityDao = db.dao)
        locationRepository = LocationRepositoryImpl(this.applicationContext)
    }
}