package no.uio.ifi.in2000.testgit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import no.uio.ifi.in2000.testgit.data.room.CityDatabase
import no.uio.ifi.in2000.testgit.ui.CityScreen
import no.uio.ifi.in2000.testgit.ui.CityViewModel
import no.uio.ifi.in2000.testgit.ui.theme.TestGitTheme
import java.io.FileOutputStream

class MainActivity : ComponentActivity() {

    /*
    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            CityDatabase::class.java,
            "cities.db"
        ).addCallback(roomCallback)
            .createFromAsset("database/cities.db")
            .fallbackToDestructiveMigration()
            .build()
    }

     */

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            CityDatabase::class.java,
            "cities"
        ).build()
    }

    private val viewModel by viewModels<CityViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return CityViewModel(db.dao) as T
                }
            }
        }
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestGitTheme {
                // A surface container using the 'background' color from the theme
                /*


                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Text("Fetch test")
                    Text("æææææææææææææææææææææææææææ")
                }

                 */
                val state by viewModel.cityUiState.collectAsState()
                CityScreen(viewModel, onEvent = viewModel::onEvent)
            }
        }
    }
}
