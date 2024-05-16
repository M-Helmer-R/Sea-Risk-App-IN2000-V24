package no.uio.ifi.in2000.testgit


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import no.uio.ifi.in2000.testgit.compose.PlaskApp
//Mainactivity for the entire app

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlaskApp()
        }
    }
}