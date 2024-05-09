package no.uio.ifi.in2000.testgit

import android.app.Application
import no.uio.ifi.in2000.testgit.data.room.CityDatabase
import no.uio.ifi.in2000.testgit.data.room.DatabaseRepository
import no.uio.ifi.in2000.testgit.data.room.DatabaseRepositoryImpl

class MainApplication : Application() {

    lateinit var databaseRepository: DatabaseRepository
    //lateinit var context : Context //

    override fun onCreate() {
        super.onCreate()
        val db = CityDatabase.getDatabase(this.applicationContext)

        //context = this.applicationContext

        databaseRepository = DatabaseRepositoryImpl(cityDao = db.dao)
    }
}