package it.thebertozz.android.housekeeping

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import it.thebertozz.android.housekeeping.managers.DatabaseManager
import it.thebertozz.android.housekeeping.managers.NotificationsManager.createNotificationChannel
import it.thebertozz.android.housekeeping.managers.NotificationsManager.scheduleNotification
import it.thebertozz.android.housekeeping.ui.theme.HousekeepingTheme
import java.util.Calendar

/**
Classe entry point dell'app Android
Imposta il database e setta il contenuto alla componente in Jetpack Compose
*/

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Inizializzazione del DB

        DatabaseManager.initializeDB(this)

        //Creazione del canale per le notifiche

        createNotificationChannel(this)

        //Contenuto Jetpack Compose

        setContent {
            HousekeepingTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HousekeepingApp()
                }
            }
        }
    }
}