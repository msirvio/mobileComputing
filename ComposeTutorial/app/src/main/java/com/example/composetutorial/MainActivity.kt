package com.example.composetutorial

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composetutorial.ui.theme.ComposeTutorialTheme
import android.Manifest
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.ServiceInfo
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import androidx.compose.runtime.mutableFloatStateOf
import androidx.core.app.ServiceCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

data class Message(val author: String, val body: String)
data class SlipWarning(val city: String, val timeStamp: String)

const val MY_CHANNEL_ID = "my_notification_channel"
const val CONVERSATION: String = "CONVERSATION"
const val INFO: String = "INFO"
const val SETTINGS: String = "SETTINGS"

lateinit var fusedLocationClient: FusedLocationProviderClient
private lateinit var sensorManager: SensorManager
private var temperatureSensor: Sensor? = null
private lateinit var sensorEventListener: SensorEventListener

private var temperature = mutableFloatStateOf(20.0f)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Sensor stuff
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        temperatureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)

        sensorEventListener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                if (event.sensor.type == Sensor.TYPE_AMBIENT_TEMPERATURE) {
                    val temp = event.values[0]
                    temperature.floatValue = temp
                }
            }
            override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
        }

        //Location stuff
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ), 1)
        }

        //Notification stuff
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
            != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS), 1)
        }

        val channel = NotificationChannel(
            MY_CHANNEL_ID,
            "Channel name",
            NotificationManager.IMPORTANCE_HIGH
        )
        channel.description = "Some channel description"

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

        setContent {
            ComposeTutorialTheme {
                NavigationManager()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        temperatureSensor?.also { temp ->
            sensorManager.registerListener(sensorEventListener, temp, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(sensorEventListener)
    }
}

@Composable
fun NavigationManager() {
    val navController = rememberNavController()
    val conversationScreen = ConversationScreen()
    val infoScreen  = InfoScreen()
    val settingsScreen = SettingsScreen()
    val context = LocalContext.current

    //Temperature sensor
    Log.d("Sensor",
        "Current Temperature: ${temperature.floatValue} °C or ${temperature.floatValue.times(1.8).plus(32)} °F")
    if (temperature.floatValue < 0) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, MY_CHANNEL_ID)
            .setSmallIcon(R.drawable.baseline_circle_notifications_24)
            .setContentTitle("It's cold!")
            .setContentText("${temperature.floatValue} °C or ${temperature.floatValue.times(1.8).plus(32)} °F")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true).build()

        val notificationManager = NotificationManagerCompat.from(context)

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS)
            == PackageManager.PERMISSION_GRANTED) {

            notificationManager.notify(1, notification)
        }
    }

    NavHost(
        navController = navController,
        startDestination = CONVERSATION
    ) {
        composable(CONVERSATION) {
            conversationScreen.Conversation(
                messages = SampleData.conversationSample,
                navigateToInfo = {
                    navController.navigate(route = INFO)
                },
                navigateToSettings = {
                    navController.navigate(route = SETTINGS)
                }
            )
        }
        composable(INFO) {
            infoScreen.Info(
                navigateToConversation = {
                    navController.navigate(route = CONVERSATION) {
                        popUpTo(route = CONVERSATION) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(SETTINGS) {
            settingsScreen.Settings(
                navigateToConversation = {
                    navController.navigate(route = CONVERSATION) {
                        popUpTo(route = CONVERSATION) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}

@Preview
@Composable
fun PreviewNavigationManager() {
    ComposeTutorialTheme {
        NavigationManager()
    }
}