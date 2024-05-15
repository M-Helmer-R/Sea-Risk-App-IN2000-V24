package no.uio.ifi.in2000.testgit

import android.app.Application
import no.uio.ifi.in2000.testgit.data.metalerts.MetAlertsRepository
import no.uio.ifi.in2000.testgit.data.nowcast.NowCastRepository
import no.uio.ifi.in2000.testgit.data.oceanforecast.OceanForeCastRepository
import no.uio.ifi.in2000.testgit.data.room.CityDatabase
import no.uio.ifi.in2000.testgit.data.room.DatabaseRepository
import no.uio.ifi.in2000.testgit.data.room.DatabaseRepositoryImpl

class MainApplication : Application() {
    lateinit var databaseRepository: DatabaseRepository
    lateinit var nowCastRepository: NowCastRepository
    lateinit var metAlertsRepository: MetAlertsRepository
    lateinit var oceanForecastRepository: OceanForeCastRepository
    override fun onCreate() {
        super.onCreate()
        val db = CityDatabase.getDatabase(this.applicationContext)
        databaseRepository = DatabaseRepositoryImpl(cityDao = db.dao)
        nowCastRepository = NowCastRepository()
        metAlertsRepository = MetAlertsRepository()
        oceanForecastRepository = OceanForeCastRepository()
    }
}