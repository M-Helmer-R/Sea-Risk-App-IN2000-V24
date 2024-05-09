package no.uio.ifi.in2000.testgit

import android.app.Application
import android.content.Context
import no.uio.ifi.in2000.testgit.data.room.CityDatabase
import no.uio.ifi.in2000.testgit.data.room.DatabaseRepository
import no.uio.ifi.in2000.testgit.data.room.DatabaseRepositoryImpl

class PlaskApplication : Application(){
    lateinit var databaseRepository: DatabaseRepository

    override fun onCreate() {
        super.onCreate()
        val db = CityDatabase.getDatabase(this.applicationContext)

        databaseRepository = DatabaseRepositoryImpl(db.dao)
    }

}